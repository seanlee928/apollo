echo "sync conf"
sh run.sh "scp -r h1:$HADOOP_HOME/conf $HADOOP_HOME"
echo "sync .bash_profile"
sh run.sh "scp -r h1:.bash_profile ."
echo "sync libexec"
sh run.sh "scp -r h1:$HADOOP_HOME/libexec $HADOOP_HOME"
