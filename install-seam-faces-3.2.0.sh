#!/bin/bash

RED='\e[0;31m'
DEFAULT='\e[0m'

echo -e "${RED}Cloning https://github.com/VaclavDedik/faces/"
echo -e "---------------------------------------------${DEFAULT}\n"

git clone https://github.com/VaclavDedik/faces/ \
&& cd faces \
&& echo -e "\n${RED}Building packages and installing into local maven repository" \
&& echo -e "---------------------------------------------${DEFAULT}\n" \
&& mvn install -s settings.xml \
&& cd .. \
&& echo -e "\n${RED}Cleaning up..." \
&& echo -e "---------------------------------------------${DEFAULT}\n" \
&& rm -rf faces \
&& echo -e "${RED}SUCCESSFULLY DONE${DEFAULT}"
