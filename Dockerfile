# Copyright 2017 TWO SIGMA OPEN SOURCE, LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#        http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

FROM ubuntu:16.04

ARG VCS_REF
ARG VERSION

LABEL org.label-schema.build-date=$BUILD_DATE \
      org.label-schema.name="BeakerX" \
      org.label-schema.description="BeakerX is a collection of kernels and extensions to the Jupyter interactive computing environment. It provides JVM support, interactive plots, tables, forms, publishing, and more." \
      org.label-schema.url="http://beakerx.com/" \
      org.label-schema.vcs-ref=$VCS_REF \
      org.label-schema.vcs-url="https://github.com/twosigma/beakerx" \
      org.label-schema.version=$VERSION \
      org.label-schema.schema-version="1.0"

MAINTAINER BeakerX Feedback <beakerx-feedback@twosigma.com>


###################
#      Setup      #
###################

RUN useradd codete --create-home

ENV CONDA_DIR /opt/conda
ENV PATH /opt/conda/bin:$PATH
ENV NB_USER codete
ENV DEBIAN_FRONTEND noninteractive

RUN apt-get update
RUN apt-get install -y --no-install-recommends mysql-server apt-utils vim mongodb redis-server couchdb apt-utils sudo curl unzip software-properties-common apt-transport-https git bzip2 wget locales
RUN apt-get dist-upgrade -y
RUN locale-gen en_US.UTF-8

# Install Yarn
RUN curl -sS https://dl.yarnpkg.com/debian/pubkey.gpg | apt-key add - && \
	echo "deb https://dl.yarnpkg.com/debian/ stable main" | tee /etc/apt/sources.list.d/yarn.list && \
	apt-get update && apt-get install yarn -y

# Install Conda
RUN echo 'export PATH=/opt/conda/bin:$PATH' > /etc/profile.d/conda.sh && \
    wget --quiet https://repo.continuum.io/miniconda/Miniconda3-latest-Linux-x86_64.sh -O ~/miniconda.sh && \
    /bin/bash ~/miniconda.sh -b -p /opt/conda && \
    rm ~/miniconda.sh

RUN apt-get clean

RUN conda create -y -n beakerx 'python>=3' nodejs pandas openjdk maven py4j
RUN conda config --env --add pinned_packages 'openjdk >8.0.121'
RUN conda install -y -n beakerx -c conda-forge ipywidgets jupyterhub jupyterlab pyzmq pytest requests cassandra-driver

ENV LANG=en_US.UTF-8
ENV LC_CTYPE=en_US.UTF-8
ENV LC_ALL=en_US.UTF-8
ENV SHELL /bin/bash
ENV NB_UID 1000
ENV HOME /home/$NB_USER

#COPY beakerx environment.yml jitpack.yml js kernel test /home/beakerx/
#COPY docker/setup.sh / $HOME/
#COPY docker/start.sh docker/start-notebook.sh docker/start-singleuser.sh /usr/local/bin/
#COPY docker/jupyter_notebook_config.py /etc/jupyter/

RUN mkdir /home/codete/github/
WORKDIR /home/codete/github/
RUN git clone https://github.com/twosigma/beakerx
WORKDIR /home/codete/github/beakerx/

RUN cp -R beakerx /home/codete/
RUN cp environment.yml /home/codete/
RUN cp jitpack.yml /home/codete
RUN cp -R js /home/codete/ 
RUN cp -R kernel /home/codete/
RUN cp -R doc /home/codete/
RUN cp -R test /home/codete/
RUN cp docker/setup.sh /home/codete/
RUN cp docker/start.sh /usr/local/bin/
RUN cp docker/start-notebook.sh /usr/local/bin/
RUN cp docker/start-singleuser.sh /usr/local/bin/
RUN mkdir /etc/jupyter
RUN cp docker/jupyter_notebook_config.py /etc/jupyter/


RUN mkdir /home/codete/workshop/

# cassandra

RUN echo "deb http://www.apache.org/dist/cassandra/debian 311x main" | tee -a /etc/apt/sources.list.d/cassandra.sources.list
RUN echo "deb https://packages.grafana.com/oss/deb stable main" | tee -a /etc/apt/sources.list.d/grafana.sources.list
#RUN echo "deb https://DSA_email_address:downloads_key@debian.datastax.com/enterprise/ stable main" | tee -a /etc/apt/sources.list.d/datastax.sources.list
RUN echo 'deb http://apt.newrelic.com/debian/ newrelic non-free' | tee /etc/apt/sources.list.d/newrelic.list
RUN curl https://www.apache.org/dist/cassandra/KEYS | apt-key add -
RUN wget -O- https://download.newrelic.com/548C16BF.gpg | apt-key add -
RUN curl https://packages.grafana.com/gpg.key | apt-key add -
RUN curl -L https://debian.datastax.com/debian/repo_key | apt-key add -
RUN apt update
# dse-full=6.0.6-1
RUN apt-get install -y sysstat cassandra grafana libaio1  newrelic-daemon nagios3 nagios-plugins-basic

ENV DD_API_KEY=dc7ad0acdea6830adaf55dc7effbb530 
ENV DD_START_AGENT=0
RUN curl -s https://raw.githubusercontent.com/DataDog/dd-agent/master/packaging/datadog-agent/source/setup_agent.sh | bash


WORKDIR /home/codete/

###################
#      Build      #
###################
RUN chown -R codete:codete /home/codete 
RUN chown -R codete:codete /opt/conda/envs/beakerx

RUN /home/codete/setup.sh
RUN chown -R codete:codete /home/codete/.config 

RUN mkdir /var/lib/data
RUN chown -R cassandra:cassandra /var/lib/data/
# Add documentation
#COPY NOTICE README.md StartHere.ipynb doc /home/beakerx/

USER codete

EXPOSE 8888

RUN git clone https://github.com/kprzystalski/cassandra-training

CMD start-notebook.sh --ip=0.0.0.0 --NotebookApp.token='' --NotebookApp.password='' --notebook-dir=/home/codete/workshop/

