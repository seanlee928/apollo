#mvn install -Dtar  -Pdist -Dmaven.javadoc.skip=true -DskipTests=true


getpasswd() {
  if [ -z $PSS ]; then
    stty -echo
    echo -n "Your UNIX Password: "
    read -e PW
    echo -e "\r"
    stty echo
  else
    PW=$(echo $PSS|base64 -d)
  fi
}
ex() {
  expect -c "set timeout -1
             log_user 0
             spawn $*
             log_user 1
             while {1} {
               expect {
                  *assword: {
                    send $PW\r
                  } *esponse: {
                    send $PW\r
                  } yes*no {
                    send yes\r
                  } y/N {
                    send y\r
                  } eof {
                    exit
                  } timeout {
                    puts EX_TIMEOUT;interact
                  }
               }
             }"
}


cpfile() {
 ex ssh aliyarn@$i "rm -rf cpdist"
 ex scp -r hadoop.tar aliyarn@$i:$cpdist
 ex ssh aliyarn@$i "tar -xf hadoop.tar"
}

getpasswd
set cpdist=/home/aliyarn/hadoop-0.23.4/share/
cd /home/dragon.caol/src/alibaba-yarn-0.23.4/hadoop-dist/target/hadoop-0.23.4/share

mvn install -Dtar  -Pdist -Dmaven.javadoc.skip=true -DskipTests=true


startTime=`date +%s`
rm hadoop.tar
tar -cf hadoop.tar hadoop/
for i in h1  h2 h3 h4
do
	date
	cpfile
	date
done
endTime=`date +%s`
echo "totalTime:"$(($endTime - $startTime))


