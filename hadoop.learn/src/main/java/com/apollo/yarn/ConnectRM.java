package com.apollo.yarn;

import org.apache.hadoop.net.NetUtils;
import org.apache.hadoop.yarn.api.ClientRMProtocol;
import org.apache.hadoop.yarn.conf.YarnConfiguration;

public class ConnectRM {

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    ClientRMProtocol applionsManager;
    YarnConfiguration yarnConf=new YarnConfiguration();
   // InetSocketAddress rmAddress = NetUtils.createSocketAddr(yarnConf.get(YarnConfiguration.RM_ADDRESS), yarnConf.get(YarnConfiguration.DEFAULT_RM_ADDRESS));
  }

}
