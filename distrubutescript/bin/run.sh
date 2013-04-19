#!/bin/sh

HOST_LIST="h2 h3 h4"
SSH_PORT=22

for loop in $HOST_LIST
do
        echo "executing in $loop:"
        ssh -p $SSH_PORT $loop "$1"
        echo
done
