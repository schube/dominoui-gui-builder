#!/bin/bash

mvn clean package gwt:compile

rm -r ./target/GuiBuilder-1.0.0/META-INF
rm -r ./target/GuiBuilder-1.0.0/WEB-INF

