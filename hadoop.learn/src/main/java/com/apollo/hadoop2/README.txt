代码运行的结果有点意思：
[mapred@r03c02038 longer]$ java TestSplit2 0
ProcessTree:0
split:7918
[mapred@r03c02038 longer]$ java TestSplit2 1
ProcessTree:183
split:10930
[mapred@r03c02038 longer]$ java TestSplit2 2
ProcessTree:315
split:8755
[mapred@r03c02038 longer]$ java TestSplit2 3
ProcessTree:435
split:10864
[mapred@r03c02038 longer]$ java TestSplit2 4
ProcessTree:537
split:10944
[mapred@r03c02038 longer]$ java TestSplit2 5
ProcessTree:640
split:10805

final use jdk1.7 
result:
[mapred@r03c02038 longer]$ java TestSplit2 0
ProcessTree:0
split:6123
[mapred@r03c02038 longer]$ java TestSplit2 1
ProcessTree:170
split:6020
[mapred@r03c02038 longer]$ java TestSplit2 2
ProcessTree:287
split:6028
[mapred@r03c02038 longer]$ java TestSplit2 10
ProcessTree:1012
split:5878
[mapred@r03c02038 longer]$ java TestSplit2 20
ProcessTree:1588
split:5897
