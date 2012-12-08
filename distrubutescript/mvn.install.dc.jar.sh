
build_home=/home/dragon.caol/src/split-hdfs-mr-2/
cd $build_home
rm -rf build
ant jar

cd $build_home/build
echo "mvn hdfs"
jar -xvf hadoop-0.19.1-dc-core.jar org/apache/hadoop/hdfs
jar -cvf hadoop-hdfs-0.19.1-dc.jar org
mvn install:install-file -DgroupId=org.apache.hadoop -DartifactId=hadoop-hdfs -Dversion=0.19.1-dc -Dfile=hadoop-hdfs-0.19.1-dc.jar -Dpackaging=jar -DgeneratePom=true
rm -rf org/

echo "mvn commom"
jar -xvf hadoop-0.19.1-dc-core.jar org
cd org/apache/hadoop/
rm -rf hdfs/ mapred/ mapreduce/
cd $build_home/build
jar -cvf hadoop-common-0.19.1-dc.jar org
mvn install:install-file -DgroupId=org.apache.hadoop -DartifactId=hadoop-common -Dversion=0.19.1-dc -Dfile=hadoop-common-0.19.1-dc.jar -Dpackaging=jar -DgeneratePom=true
rm -rf org/



