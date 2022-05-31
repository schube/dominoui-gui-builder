#!/bin/bash

rsync --archive \
	--stats \
	--verbose \
	--compress \
	 --partial \
	--progress \
	-e "ssh -p 45924"  ./target/GuiBuilder-1.0.0/	root@176.221.46.125:/var/www/K3000002/www.schubec.com/htdocs/guibuilder
