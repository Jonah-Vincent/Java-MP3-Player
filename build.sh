#! /bin/bash
export PATH=$PATH:/Volumes/CSCDRIVE/Java/ant-1.9.9/bin/bash
export PATH=$PATH:/Volumes/CSCDRIVE/Java/bin


clear

ant run -buildfile unix_build.xml
#ant robot -buildfile unix_build.xml
