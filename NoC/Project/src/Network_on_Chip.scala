

import Chisel._
import NetworkInterface._
import Router._

/**
 * @author patmos
 */
class Network_on_Chip extends Module {
  val io = new Bundle {
    
    /*Inputs for testing*/
    val Input_Valid_Processor_4 = Bool(INPUT)
    val Input_Processor_4          = UInt(INPUT,width=32)
    val Destination_Address_Processor_4 = UInt(INPUT,width=4)
    
    val Input_Valid_Processor_5 = Bool(INPUT)
    val Input_Processor_5          = UInt(INPUT,width=32)
    val Destination_Address_Processor_5 = UInt(INPUT,width=4)
    
    val Input_Valid_Processor_12 = Bool(INPUT)
    val Input_Processor_12          = UInt(INPUT,width=32)
    val Destination_Address_Processor_12 = UInt(INPUT,width=4)

 
  }

  /*Router and Network Interface instantiation*/
  val NetworkInterface_0 = Module(new NetworkInterface(UInt(0)))
  val Router_0 = Module(new Router)
  
  val NetworkInterface_1 = Module(new NetworkInterface(UInt(1)))
  val Router_1 = Module(new Router)
  
  val NetworkInterface_2 = Module(new NetworkInterface(UInt(2)))
  val Router_2 = Module(new Router)
  
  val NetworkInterface_3 = Module(new NetworkInterface(UInt(3)))
  val Router_3 = Module(new Router)
  
  val NetworkInterface_4 = Module(new NetworkInterface(UInt(4)))
  val Router_4 = Module(new Router)
  
  val NetworkInterface_5 = Module(new NetworkInterface(UInt(5)))
  val Router_5 = Module(new Router)
  
  val NetworkInterface_6 = Module(new NetworkInterface(UInt(6)))
  val Router_6 = Module(new Router)
  
  val NetworkInterface_7 = Module(new NetworkInterface(UInt(7)))
  val Router_7 = Module(new Router)
  
  val NetworkInterface_8 = Module(new NetworkInterface(UInt(8)))
  val Router_8 = Module(new Router)
  
  val NetworkInterface_9 = Module(new NetworkInterface(UInt(9)))
  val Router_9 = Module(new Router)
  
  val NetworkInterface_10 = Module(new NetworkInterface(UInt(10)))
  val Router_10 = Module(new Router)
  
  val NetworkInterface_11 = Module(new NetworkInterface(UInt(11)))
  val Router_11 = Module(new Router)

  val NetworkInterface_12 = Module(new NetworkInterface(UInt(12)))
  val Router_12 = Module(new Router)
  
  val NetworkInterface_13 = Module(new NetworkInterface(UInt(13)))
  val Router_13 = Module(new Router)
  
  val NetworkInterface_14 = Module(new NetworkInterface(UInt(14)))
  val Router_14 = Module(new Router)
  
  val NetworkInterface_15 = Module(new NetworkInterface(UInt(15)))
  val Router_15 = Module(new Router)
  
  
  /*CONNECTIONS*/
  
  //Router 0 - Network Interface 0
  //NI Output - Router Input
  Router_0.io.Input_Processor_Input                       := NetworkInterface_0.io.Output
  Router_0.io.Input_Processor_Data_Valid               := NetworkInterface_0.io.Output_Data_Valid
  NetworkInterface_0.io.Input_ACK                         := Router_0.io.Input_Processor_Input_ACK
  NetworkInterface_0.io.Input_Ready                      := Router_0.io.Input_Processor_Input_Ready
  //NI Input - Router Output
  NetworkInterface_0.io.Router_Input                     := Router_0.io.Output_Port_P_Output
  NetworkInterface_0.io.Router_Input_Data_Valid   := Router_0.io.Output_Port_P_Data_Valid
  Router_0.io.Output_Port_P_ACK                          := NetworkInterface_0.io.Router_Input_ACK
  Router_0.io.Output_Port_P_Ready                       := NetworkInterface_0.io.Router_Input_Ready

  //Router 1 - Network Interface 1
  //NI Output - Router Input
  Router_1.io.Input_Processor_Input                       := NetworkInterface_1.io.Output
  Router_1.io.Input_Processor_Data_Valid               := NetworkInterface_1.io.Output_Data_Valid
  NetworkInterface_1.io.Input_ACK                         := Router_1.io.Input_Processor_Input_ACK
  NetworkInterface_1.io.Input_Ready                      := Router_1.io.Input_Processor_Input_Ready
  //NI Input - Router Output
  NetworkInterface_1.io.Router_Input                     := Router_1.io.Output_Port_P_Output
  NetworkInterface_1.io.Router_Input_Data_Valid   := Router_1.io.Output_Port_P_Data_Valid
  Router_1.io.Output_Port_P_ACK                          := NetworkInterface_1.io.Router_Input_ACK
  Router_1.io.Output_Port_P_Ready                       := NetworkInterface_1.io.Router_Input_Ready


  //Router 2 - Network Interface 2
  //NI Output - Router Input
  Router_2.io.Input_Processor_Input                       := NetworkInterface_1.io.Output
  Router_2.io.Input_Processor_Data_Valid               := NetworkInterface_1.io.Output_Data_Valid
  NetworkInterface_2.io.Input_ACK                         := Router_2.io.Input_Processor_Input_ACK
  NetworkInterface_2.io.Input_Ready                      := Router_2.io.Input_Processor_Input_Ready
  //NI Input - Router Output
  NetworkInterface_2.io.Router_Input                     := Router_2.io.Output_Port_P_Output
  NetworkInterface_2.io.Router_Input_Data_Valid   := Router_2.io.Output_Port_P_Data_Valid
  Router_2.io.Output_Port_P_ACK                          := NetworkInterface_2.io.Router_Input_ACK
  Router_2.io.Output_Port_P_Ready                       := NetworkInterface_2.io.Router_Input_Ready

  //Router 3 - Network Interface 1
  //NI Output - Router Input
  Router_3.io.Input_Processor_Input                       := NetworkInterface_3.io.Output
  Router_3.io.Input_Processor_Data_Valid               := NetworkInterface_3.io.Output_Data_Valid
  NetworkInterface_3.io.Input_ACK                         := Router_3.io.Input_Processor_Input_ACK
  NetworkInterface_3.io.Input_Ready                      := Router_3.io.Input_Processor_Input_Ready
  //NI Input - Router Output
  NetworkInterface_3.io.Router_Input                     := Router_3.io.Output_Port_P_Output
  NetworkInterface_3.io.Router_Input_Data_Valid   := Router_3.io.Output_Port_P_Data_Valid
  Router_3.io.Output_Port_P_ACK                          := NetworkInterface_3.io.Router_Input_ACK
  Router_3.io.Output_Port_P_Ready                       := NetworkInterface_3.io.Router_Input_Ready

  //Router 4 - Network Interface 1
  //NI Output - Router Input
  Router_4.io.Input_Processor_Input                       := NetworkInterface_4.io.Output
  Router_4.io.Input_Processor_Data_Valid               := NetworkInterface_4.io.Output_Data_Valid
  NetworkInterface_4.io.Input_ACK                         := Router_4.io.Input_Processor_Input_ACK
  NetworkInterface_4.io.Input_Ready                      := Router_4.io.Input_Processor_Input_Ready
  //NI Input - Router Output
  NetworkInterface_4.io.Router_Input                     := Router_4.io.Output_Port_P_Output
  NetworkInterface_4.io.Router_Input_Data_Valid   := Router_4.io.Output_Port_P_Data_Valid
  Router_4.io.Output_Port_P_ACK                          := NetworkInterface_4.io.Router_Input_ACK
  Router_4.io.Output_Port_P_Ready                       := NetworkInterface_4.io.Router_Input_Ready

  //Router 5 - Network Interface 1
  //NI Output - Router Input
  Router_5.io.Input_Processor_Input                       := NetworkInterface_5.io.Output
  Router_5.io.Input_Processor_Data_Valid               := NetworkInterface_5.io.Output_Data_Valid
  NetworkInterface_5.io.Input_ACK                         := Router_5.io.Input_Processor_Input_ACK
  NetworkInterface_5.io.Input_Ready                      := Router_5.io.Input_Processor_Input_Ready
  //NI Input - Router Output
  NetworkInterface_5.io.Router_Input                     := Router_5.io.Output_Port_P_Output
  NetworkInterface_5.io.Router_Input_Data_Valid   := Router_5.io.Output_Port_P_Data_Valid
  Router_5.io.Output_Port_P_ACK                          := NetworkInterface_5.io.Router_Input_ACK
  Router_5.io.Output_Port_P_Ready                       := NetworkInterface_5.io.Router_Input_Ready

  //Router 6 - Network Interface 1
  //NI Output - Router Input
  Router_6.io.Input_Processor_Input                       := NetworkInterface_6.io.Output
  Router_6.io.Input_Processor_Data_Valid               := NetworkInterface_6.io.Output_Data_Valid
  NetworkInterface_6.io.Input_ACK                         := Router_6.io.Input_Processor_Input_ACK
  NetworkInterface_6.io.Input_Ready                      := Router_6.io.Input_Processor_Input_Ready
  //NI Input - Router Output
  NetworkInterface_6.io.Router_Input                     := Router_6.io.Output_Port_P_Output
  NetworkInterface_6.io.Router_Input_Data_Valid   := Router_6.io.Output_Port_P_Data_Valid
  Router_6.io.Output_Port_P_ACK                          := NetworkInterface_6.io.Router_Input_ACK
  Router_6.io.Output_Port_P_Ready                       := NetworkInterface_6.io.Router_Input_Ready

  //Router 7 - Network Interface 1
  //NI Output - Router Input
  Router_7.io.Input_Processor_Input                       := NetworkInterface_7.io.Output
  Router_7.io.Input_Processor_Data_Valid               := NetworkInterface_7.io.Output_Data_Valid
  NetworkInterface_7.io.Input_ACK                         := Router_7.io.Input_Processor_Input_ACK
  NetworkInterface_7.io.Input_Ready                      := Router_7.io.Input_Processor_Input_Ready
  //NI Input - Router Output
  NetworkInterface_7.io.Router_Input                     := Router_7.io.Output_Port_P_Output
  NetworkInterface_7.io.Router_Input_Data_Valid   := Router_7.io.Output_Port_P_Data_Valid
  Router_7.io.Output_Port_P_ACK                          := NetworkInterface_7.io.Router_Input_ACK
  Router_7.io.Output_Port_P_Ready                       := NetworkInterface_7.io.Router_Input_Ready

  //Router 8 - Network Interface 1
  //NI Output - Router Input
  Router_8.io.Input_Processor_Input                       := NetworkInterface_8.io.Output
  Router_8.io.Input_Processor_Data_Valid               := NetworkInterface_8.io.Output_Data_Valid
  NetworkInterface_8.io.Input_ACK                         := Router_8.io.Input_Processor_Input_ACK
  NetworkInterface_8.io.Input_Ready                      := Router_8.io.Input_Processor_Input_Ready
  //NI Input - Router Output
  NetworkInterface_8.io.Router_Input                     := Router_8.io.Output_Port_P_Output
  NetworkInterface_8.io.Router_Input_Data_Valid   := Router_8.io.Output_Port_P_Data_Valid
  Router_8.io.Output_Port_P_ACK                          := NetworkInterface_8.io.Router_Input_ACK
  Router_8.io.Output_Port_P_Ready                       := NetworkInterface_8.io.Router_Input_Ready

  //Router 9 - Network Interface 1
  //NI Output - Router Input
  Router_9.io.Input_Processor_Input                       := NetworkInterface_9.io.Output
  Router_9.io.Input_Processor_Data_Valid               := NetworkInterface_9.io.Output_Data_Valid
  NetworkInterface_9.io.Input_ACK                         := Router_9.io.Input_Processor_Input_ACK
  NetworkInterface_9.io.Input_Ready                      := Router_9.io.Input_Processor_Input_Ready
  //NI Input - Router Output
  NetworkInterface_9.io.Router_Input                     := Router_9.io.Output_Port_P_Output
  NetworkInterface_9.io.Router_Input_Data_Valid   := Router_9.io.Output_Port_P_Data_Valid
  Router_9.io.Output_Port_P_ACK                          := NetworkInterface_9.io.Router_Input_ACK
  Router_9.io.Output_Port_P_Ready                       := NetworkInterface_9.io.Router_Input_Ready

  //Router 10 - Network Interface 1
  //NI Output - Router Input
  Router_10.io.Input_Processor_Input                       := NetworkInterface_10.io.Output
  Router_10.io.Input_Processor_Data_Valid               := NetworkInterface_10.io.Output_Data_Valid
  NetworkInterface_10.io.Input_ACK                         := Router_10.io.Input_Processor_Input_ACK
  NetworkInterface_10.io.Input_Ready                      := Router_10.io.Input_Processor_Input_Ready
  //NI Input - Router Output
  NetworkInterface_10.io.Router_Input                     := Router_10.io.Output_Port_P_Output
  NetworkInterface_10.io.Router_Input_Data_Valid   := Router_10.io.Output_Port_P_Data_Valid
  Router_10.io.Output_Port_P_ACK                          := NetworkInterface_10.io.Router_Input_ACK
  Router_10.io.Output_Port_P_Ready                       := NetworkInterface_10.io.Router_Input_Ready

  //Router 11 - Network Interface 1
  //NI Output - Router Input
  Router_11.io.Input_Processor_Input                       := NetworkInterface_11.io.Output
  Router_11.io.Input_Processor_Data_Valid               := NetworkInterface_11.io.Output_Data_Valid
  NetworkInterface_11.io.Input_ACK                         := Router_11.io.Input_Processor_Input_ACK
  NetworkInterface_11.io.Input_Ready                      := Router_11.io.Input_Processor_Input_Ready
  //NI Input - Router Output
  NetworkInterface_11.io.Router_Input                     := Router_11.io.Output_Port_P_Output
  NetworkInterface_11.io.Router_Input_Data_Valid   := Router_11.io.Output_Port_P_Data_Valid
  Router_11.io.Output_Port_P_ACK                          := NetworkInterface_11.io.Router_Input_ACK
  Router_11.io.Output_Port_P_Ready                       := NetworkInterface_11.io.Router_Input_Ready

  //Router 12 - Network Interface 1
  //NI Output - Router Input
  Router_12.io.Input_Processor_Input                       := NetworkInterface_12.io.Output
  Router_12.io.Input_Processor_Data_Valid               := NetworkInterface_12.io.Output_Data_Valid
  NetworkInterface_12.io.Input_ACK                         := Router_12.io.Input_Processor_Input_ACK
  NetworkInterface_12.io.Input_Ready                      := Router_12.io.Input_Processor_Input_Ready
  //NI Input - Router Output
  NetworkInterface_12.io.Router_Input                     := Router_12.io.Output_Port_P_Output
  NetworkInterface_12.io.Router_Input_Data_Valid   := Router_12.io.Output_Port_P_Data_Valid
  Router_12.io.Output_Port_P_ACK                          := NetworkInterface_12.io.Router_Input_ACK
  Router_12.io.Output_Port_P_Ready                       := NetworkInterface_12.io.Router_Input_Ready

  //Router 13 - Network Interface 1
  //NI Output - Router Input
  Router_13.io.Input_Processor_Input                       := NetworkInterface_13.io.Output
  Router_13.io.Input_Processor_Data_Valid               := NetworkInterface_13.io.Output_Data_Valid
  NetworkInterface_13.io.Input_ACK                         := Router_13.io.Input_Processor_Input_ACK
  NetworkInterface_13.io.Input_Ready                      := Router_13.io.Input_Processor_Input_Ready
  //NI Input - Router Output
  NetworkInterface_13.io.Router_Input                     := Router_13.io.Output_Port_P_Output
  NetworkInterface_13.io.Router_Input_Data_Valid   := Router_13.io.Output_Port_P_Data_Valid
  Router_13.io.Output_Port_P_ACK                          := NetworkInterface_13.io.Router_Input_ACK
  Router_13.io.Output_Port_P_Ready                       := NetworkInterface_13.io.Router_Input_Ready

  //Router 14 - Network Interface 1
  //NI Output - Router Input
  Router_14.io.Input_Processor_Input                       := NetworkInterface_14.io.Output
  Router_14.io.Input_Processor_Data_Valid               := NetworkInterface_14.io.Output_Data_Valid
  NetworkInterface_14.io.Input_ACK                         := Router_14.io.Input_Processor_Input_ACK
  NetworkInterface_14.io.Input_Ready                      := Router_14.io.Input_Processor_Input_Ready
  //NI Input - Router Output
  NetworkInterface_14.io.Router_Input                     := Router_14.io.Output_Port_P_Output
  NetworkInterface_14.io.Router_Input_Data_Valid   := Router_14.io.Output_Port_P_Data_Valid
  Router_14.io.Output_Port_P_ACK                          := NetworkInterface_14.io.Router_Input_ACK
  Router_14.io.Output_Port_P_Ready                       := NetworkInterface_14.io.Router_Input_Ready

  //Router 15 - Network Interface 1
  //NI Output - Router Input
  Router_15.io.Input_Processor_Input                       := NetworkInterface_15.io.Output
  Router_15.io.Input_Processor_Data_Valid               := NetworkInterface_15.io.Output_Data_Valid
  NetworkInterface_15.io.Input_ACK                         := Router_15.io.Input_Processor_Input_ACK
  NetworkInterface_15.io.Input_Ready                      := Router_15.io.Input_Processor_Input_Ready
  //NI Input - Router Output
  NetworkInterface_15.io.Router_Input                     := Router_15.io.Output_Port_P_Output
  NetworkInterface_15.io.Router_Input_Data_Valid   := Router_15.io.Output_Port_P_Data_Valid
  Router_15.io.Output_Port_P_ACK                          := NetworkInterface_15.io.Router_Input_ACK
  Router_15.io.Output_Port_P_Ready                       := NetworkInterface_15.io.Router_Input_Ready


  /*************************** MESH TOPOLOGY *****************************
  Topology:
  *                    
  *                                 Port 2
  *                                   __
  *                     Port 1    |    |  Port 3
  *                                  |__|
  *                                  Port 4
  *                                                  
  *                     12 - 13 -- 14 --15   
  *                     |       |       |      |
  *                     8 --- 9 ---10 --11   
  *                     |       |       |      |
  *                     4 --- 5 --- 6 ---7   
  *                     |       |       |      |
  *                     0 --- 1 --- 2 ---3
  * */
  
  //HORIZONTAL CONNECTIONS
 
  //Router 0 Port 3 OUT - Router 1 Port 1 IN
  Router_1.io.Input_Port_1_Data_Valid   :=    Router_0.io.Output_Port_3_Data_Valid
  Router_1.io.Input_Port_1_Input           :=    Router_0.io.Output_Port_3_Output
  Router_0.io.Output_Port_3_ACK          :=    Router_1.io.Input_Port_1_Input_ACK
  Router_0.io.Output_Port_3_Ready       :=    Router_1.io.Input_Port_1_Input_Ready  
  //Router 1 Port 1 OUT - Router 0 Port 3 IN
  Router_0.io.Input_Port_3_Data_Valid   :=    Router_1.io.Output_Port_1_Data_Valid
  Router_0.io.Input_Port_3_Input           :=    Router_1.io.Output_Port_1_Output
  Router_1.io.Output_Port_1_ACK          :=    Router_0.io.Input_Port_3_Input_ACK
  Router_1.io.Output_Port_1_Ready       :=    Router_0.io.Input_Port_3_Input_Ready
   
  //Router 1 Port 3 OUT - Router 2 Port 1 IN
  Router_2.io.Input_Port_1_Data_Valid   :=    Router_1.io.Output_Port_3_Data_Valid
  Router_2.io.Input_Port_1_Input           :=    Router_1.io.Output_Port_3_Output
  Router_1.io.Output_Port_3_ACK          :=    Router_2.io.Input_Port_1_Input_ACK
  Router_1.io.Output_Port_3_Ready       :=    Router_2.io.Input_Port_1_Input_Ready 
  //Router 2 Port 1 OUT - Router 3 Port 3 IN
  Router_3.io.Input_Port_3_Data_Valid   :=    Router_2.io.Output_Port_1_Data_Valid
  Router_3.io.Input_Port_3_Input           :=    Router_2.io.Output_Port_1_Output
  Router_2.io.Output_Port_1_ACK          :=    Router_3.io.Input_Port_3_Input_ACK
  Router_2.io.Output_Port_1_Ready       :=    Router_3.io.Input_Port_3_Input_Ready
     
  //Router 2 Port 3 OUT - Router 3 Port 1 IN
  Router_3.io.Input_Port_1_Data_Valid   :=    Router_2.io.Output_Port_3_Data_Valid
  Router_3.io.Input_Port_1_Input           :=    Router_2.io.Output_Port_3_Output
  Router_2.io.Output_Port_3_ACK          :=    Router_3.io.Input_Port_1_Input_ACK
  Router_2.io.Output_Port_3_Ready       :=    Router_3.io.Input_Port_1_Input_Ready 
  //Router 3 Port 1 OUT - Router 2 Port 3 IN
  Router_2.io.Input_Port_3_Data_Valid   :=    Router_3.io.Output_Port_1_Data_Valid
  Router_2.io.Input_Port_3_Input           :=    Router_3.io.Output_Port_1_Output
  Router_3.io.Output_Port_1_ACK          :=    Router_2.io.Input_Port_3_Input_ACK
  Router_3.io.Output_Port_1_Ready       :=    Router_2.io.Input_Port_3_Input_Ready
      
  //Router 4 Port 3 OUT - Router 5 Port 1 IN
  Router_5.io.Input_Port_1_Data_Valid   :=    Router_4.io.Output_Port_3_Data_Valid
  Router_5.io.Input_Port_1_Input           :=    Router_4.io.Output_Port_3_Output
  Router_4.io.Output_Port_3_ACK          :=    Router_5.io.Input_Port_1_Input_ACK
  Router_4.io.Output_Port_3_Ready       :=    Router_5.io.Input_Port_1_Input_Ready 
  //Router 5 Port 1 OUT - Router 4 Port 3 IN
  Router_4.io.Input_Port_3_Data_Valid   :=    Router_5.io.Output_Port_1_Data_Valid
  Router_4.io.Input_Port_3_Input           :=    Router_5.io.Output_Port_1_Output
  Router_5.io.Output_Port_1_ACK          :=    Router_4.io.Input_Port_3_Input_ACK
  Router_5.io.Output_Port_1_Ready       :=    Router_4.io.Input_Port_3_Input_Ready
        
  //Router 5 Port 3 OUT - Router 6 Port 1 IN
  Router_6.io.Input_Port_1_Data_Valid   :=    Router_5.io.Output_Port_3_Data_Valid
  Router_6.io.Input_Port_1_Input           :=    Router_5.io.Output_Port_3_Output
  Router_5.io.Output_Port_3_ACK          :=    Router_6.io.Input_Port_1_Input_ACK
  Router_5.io.Output_Port_3_Ready       :=    Router_6.io.Input_Port_1_Input_Ready 
  //Router 6 Port 1 OUT - Router 5 Port 3 IN
  Router_5.io.Input_Port_3_Data_Valid   :=    Router_6.io.Output_Port_1_Data_Valid
  Router_5.io.Input_Port_3_Input           :=    Router_6.io.Output_Port_1_Output
  Router_6.io.Output_Port_1_ACK          :=    Router_5.io.Input_Port_3_Input_ACK
  Router_6.io.Output_Port_1_Ready       :=    Router_5.io.Input_Port_3_Input_Ready
         
  //Router 6 Port 3 OUT - Router 7 Port 1 IN
  Router_7.io.Input_Port_1_Data_Valid   :=    Router_6.io.Output_Port_3_Data_Valid
  Router_7.io.Input_Port_1_Input           :=    Router_6.io.Output_Port_3_Output
  Router_6.io.Output_Port_3_ACK          :=    Router_7.io.Input_Port_1_Input_ACK
  Router_6.io.Output_Port_3_Ready       :=    Router_7.io.Input_Port_1_Input_Ready 
  //Router 7 Port 1 OUT - Router 6 Port 3 IN
  Router_6.io.Input_Port_3_Data_Valid   :=    Router_7.io.Output_Port_1_Data_Valid
  Router_6.io.Input_Port_3_Input           :=    Router_7.io.Output_Port_1_Output
  Router_7.io.Output_Port_1_ACK          :=    Router_6.io.Input_Port_3_Input_ACK
  Router_7.io.Output_Port_1_Ready       :=    Router_6.io.Input_Port_3_Input_Ready
           
  //Router 8 Port 3 OUT - Router 9 Port 1 IN
  Router_9.io.Input_Port_1_Data_Valid   :=    Router_8.io.Output_Port_3_Data_Valid
  Router_9.io.Input_Port_1_Input           :=    Router_8.io.Output_Port_3_Output
  Router_8.io.Output_Port_3_ACK          :=    Router_9.io.Input_Port_1_Input_ACK
  Router_8.io.Output_Port_3_Ready       :=    Router_9.io.Input_Port_1_Input_Ready 
  //Router 9 Port 1 OUT - Router 8 Port 3 IN
  Router_8.io.Input_Port_3_Data_Valid   :=    Router_9.io.Output_Port_1_Data_Valid
  Router_8.io.Input_Port_3_Input           :=    Router_9.io.Output_Port_1_Output
  Router_9.io.Output_Port_1_ACK          :=    Router_8.io.Input_Port_3_Input_ACK
  Router_9.io.Output_Port_1_Ready       :=    Router_8.io.Input_Port_3_Input_Ready
             
  //Router 9 Port 3 OUT - Router 10 Port 1 IN
  Router_10.io.Input_Port_1_Data_Valid   :=    Router_9.io.Output_Port_3_Data_Valid
  Router_10.io.Input_Port_1_Input           :=    Router_9.io.Output_Port_3_Output
  Router_9.io.Output_Port_3_ACK          :=    Router_10.io.Input_Port_1_Input_ACK
  Router_9.io.Output_Port_3_Ready       :=    Router_10.io.Input_Port_1_Input_Ready 
  //Router 10 Port 1 OUT - Router 9 Port 3 IN
  Router_9.io.Input_Port_3_Data_Valid   :=    Router_10.io.Output_Port_1_Data_Valid
  Router_9.io.Input_Port_3_Input           :=    Router_10.io.Output_Port_1_Output
  Router_10.io.Output_Port_1_ACK          :=    Router_9.io.Input_Port_3_Input_ACK
  Router_10.io.Output_Port_1_Ready       :=    Router_9.io.Input_Port_3_Input_Ready
               
  //Router 10 Port 3 OUT - Router 11 Port 1 IN
  Router_11.io.Input_Port_1_Data_Valid   :=    Router_10.io.Output_Port_3_Data_Valid
  Router_11.io.Input_Port_1_Input           :=    Router_10.io.Output_Port_3_Output
  Router_10.io.Output_Port_3_ACK          :=    Router_11.io.Input_Port_1_Input_ACK
  Router_10.io.Output_Port_3_Ready       :=    Router_11.io.Input_Port_1_Input_Ready 
  //Router 10 Port 1 OUT - Router 9 Port 3 IN
  Router_10.io.Input_Port_3_Data_Valid   :=    Router_11.io.Output_Port_1_Data_Valid
  Router_10.io.Input_Port_3_Input           :=    Router_11.io.Output_Port_1_Output
  Router_11.io.Output_Port_1_ACK          :=    Router_10.io.Input_Port_3_Input_ACK
  Router_11.io.Output_Port_1_Ready       :=    Router_10.io.Input_Port_3_Input_Ready
                 
  //Router 12 Port 3 OUT - Router 13 Port 1 IN
  Router_13.io.Input_Port_1_Data_Valid   :=    Router_12.io.Output_Port_3_Data_Valid
  Router_13.io.Input_Port_1_Input           :=    Router_12.io.Output_Port_3_Output
  Router_12.io.Output_Port_3_ACK          :=    Router_13.io.Input_Port_1_Input_ACK
  Router_12.io.Output_Port_3_Ready       :=    Router_13.io.Input_Port_1_Input_Ready 
  //Router 13 Port 1 OUT - Router 12 Port 3 IN
  Router_12.io.Input_Port_3_Data_Valid   :=    Router_13.io.Output_Port_1_Data_Valid
  Router_12.io.Input_Port_3_Input           :=    Router_13.io.Output_Port_1_Output
  Router_13.io.Output_Port_1_ACK          :=    Router_12.io.Input_Port_3_Input_ACK
  Router_13.io.Output_Port_1_Ready       :=    Router_12.io.Input_Port_3_Input_Ready
                   
  //Router 13 Port 3 OUT - Router 14 Port 1 IN
  Router_14.io.Input_Port_1_Data_Valid   :=    Router_13.io.Output_Port_3_Data_Valid
  Router_14.io.Input_Port_1_Input           :=    Router_13.io.Output_Port_3_Output
  Router_13.io.Output_Port_3_ACK          :=    Router_14.io.Input_Port_1_Input_ACK
  Router_13.io.Output_Port_3_Ready       :=    Router_14.io.Input_Port_1_Input_Ready 
  //Router 14 Port 1 OUT - Router 13 Port 3 IN
  Router_13.io.Input_Port_3_Data_Valid   :=    Router_14.io.Output_Port_1_Data_Valid
  Router_13.io.Input_Port_3_Input           :=    Router_14.io.Output_Port_1_Output
  Router_14.io.Output_Port_1_ACK          :=    Router_13.io.Input_Port_3_Input_ACK
  Router_14.io.Output_Port_1_Ready       :=    Router_13.io.Input_Port_3_Input_Ready
                     
  //Router 14 Port 3 OUT - Router 15 Port 1 IN
  Router_15.io.Input_Port_1_Data_Valid   :=    Router_14.io.Output_Port_3_Data_Valid
  Router_15.io.Input_Port_1_Input           :=    Router_14.io.Output_Port_3_Output
  Router_14.io.Output_Port_3_ACK          :=    Router_15.io.Input_Port_1_Input_ACK
  Router_14.io.Output_Port_3_Ready       :=    Router_15.io.Input_Port_1_Input_Ready 
  //Router 15 Port 1 OUT - Router 14 Port 3 IN
  Router_14.io.Input_Port_3_Data_Valid   :=    Router_15.io.Output_Port_1_Data_Valid
  Router_14.io.Input_Port_3_Input           :=    Router_15.io.Output_Port_1_Output
  Router_15.io.Output_Port_1_ACK          :=    Router_14.io.Input_Port_3_Input_ACK
  Router_15.io.Output_Port_1_Ready       :=    Router_14.io.Input_Port_3_Input_Ready
  
  // VERTICAL CONNECTIONS
  
  //Router 0 Port 2 OUT - Router 4 Port 4 IN
  Router_4.io.Input_Port_4_Data_Valid   :=    Router_0.io.Output_Port_2_Data_Valid
  Router_4.io.Input_Port_4_Input           :=    Router_0.io.Output_Port_2_Output
  Router_0.io.Output_Port_2_ACK          :=    Router_4.io.Input_Port_4_Input_ACK
  Router_0.io.Output_Port_2_Ready       :=    Router_4.io.Input_Port_4_Input_Ready
  //Router 4 Port 4 OUT - Router 0 Port 2 IN
  Router_0.io.Input_Port_2_Data_Valid   :=    Router_4.io.Output_Port_4_Data_Valid
  Router_0.io.Input_Port_2_Input           :=    Router_4.io.Output_Port_4_Output
  Router_4.io.Output_Port_4_ACK          :=    Router_0.io.Input_Port_2_Input_ACK
  Router_4.io.Output_Port_4_Ready       :=    Router_0.io.Input_Port_2_Input_Ready     
    
  //Router 1 Port 2 OUT - Router 5 Port 4 IN
  Router_5.io.Input_Port_4_Data_Valid   :=    Router_1.io.Output_Port_2_Data_Valid
  Router_5.io.Input_Port_4_Input           :=    Router_1.io.Output_Port_2_Output
  Router_1.io.Output_Port_2_ACK          :=    Router_5.io.Input_Port_4_Input_ACK
  Router_1.io.Output_Port_2_Ready       :=    Router_5.io.Input_Port_4_Input_Ready
  //Router 5 Port 4 OUT - Router 1 Port 2 IN
  Router_1.io.Input_Port_2_Data_Valid   :=    Router_5.io.Output_Port_4_Data_Valid
  Router_1.io.Input_Port_2_Input           :=    Router_5.io.Output_Port_4_Output
  Router_5.io.Output_Port_4_ACK          :=    Router_1.io.Input_Port_2_Input_ACK
  Router_5.io.Output_Port_4_Ready       :=    Router_1.io.Input_Port_2_Input_Ready     
      
  //Router 2 Port 2 OUT - Router 6 Port 4 IN
  Router_6.io.Input_Port_4_Data_Valid   :=    Router_2.io.Output_Port_2_Data_Valid
  Router_6.io.Input_Port_4_Input           :=    Router_2.io.Output_Port_2_Output
  Router_2.io.Output_Port_2_ACK          :=    Router_6.io.Input_Port_4_Input_ACK
  Router_2.io.Output_Port_2_Ready       :=    Router_6.io.Input_Port_4_Input_Ready
  //Router 6 Port 4 OUT - Router 2 Port 2 IN
  Router_2.io.Input_Port_2_Data_Valid   :=    Router_6.io.Output_Port_4_Data_Valid
  Router_2.io.Input_Port_2_Input           :=    Router_6.io.Output_Port_4_Output
  Router_6.io.Output_Port_4_ACK          :=    Router_2.io.Input_Port_2_Input_ACK
  Router_6.io.Output_Port_4_Ready       :=    Router_2.io.Input_Port_2_Input_Ready     
        
  //Router 3 Port 2 OUT - Router 7 Port 4 IN
  Router_7.io.Input_Port_4_Data_Valid   :=    Router_3.io.Output_Port_2_Data_Valid
  Router_7.io.Input_Port_4_Input           :=    Router_3.io.Output_Port_2_Output
  Router_3.io.Output_Port_2_ACK          :=    Router_7.io.Input_Port_4_Input_ACK
  Router_3.io.Output_Port_2_Ready       :=    Router_7.io.Input_Port_4_Input_Ready
  //Router 7 Port 4 OUT - Router 3 Port 2 IN
  Router_3.io.Input_Port_2_Data_Valid   :=    Router_7.io.Output_Port_4_Data_Valid
  Router_3.io.Input_Port_2_Input           :=    Router_7.io.Output_Port_4_Output
  Router_7.io.Output_Port_4_ACK          :=    Router_3.io.Input_Port_2_Input_ACK
  Router_7.io.Output_Port_4_Ready       :=    Router_3.io.Input_Port_2_Input_Ready     
          
  //Router 4 Port 2 OUT - Router 8 Port 4 IN
  Router_8.io.Input_Port_4_Data_Valid   :=    Router_4.io.Output_Port_2_Data_Valid
  Router_8.io.Input_Port_4_Input           :=    Router_4.io.Output_Port_2_Output
  Router_4.io.Output_Port_2_ACK          :=    Router_8.io.Input_Port_4_Input_ACK
  Router_4.io.Output_Port_2_Ready       :=    Router_8.io.Input_Port_4_Input_Ready
  //Router 8 Port 4 OUT - Router 4 Port 2 IN
  Router_4.io.Input_Port_2_Data_Valid   :=    Router_8.io.Output_Port_4_Data_Valid
  Router_4.io.Input_Port_2_Input           :=    Router_8.io.Output_Port_4_Output
  Router_8.io.Output_Port_4_ACK          :=    Router_4.io.Input_Port_2_Input_ACK
  Router_8.io.Output_Port_4_Ready       :=    Router_4.io.Input_Port_2_Input_Ready     
            
  //Router 5 Port 2 OUT - Router 9 Port 4 IN
  Router_9.io.Input_Port_4_Data_Valid   :=    Router_5.io.Output_Port_2_Data_Valid
  Router_9.io.Input_Port_4_Input           :=    Router_5.io.Output_Port_2_Output
  Router_5.io.Output_Port_2_ACK          :=    Router_9.io.Input_Port_4_Input_ACK
  Router_5.io.Output_Port_2_Ready       :=    Router_9.io.Input_Port_4_Input_Ready
  //Router 9 Port 4 OUT - Router 5 Port 2 IN
  Router_5.io.Input_Port_2_Data_Valid   :=    Router_9.io.Output_Port_4_Data_Valid
  Router_5.io.Input_Port_2_Input           :=    Router_9.io.Output_Port_4_Output
  Router_9.io.Output_Port_4_ACK          :=    Router_5.io.Input_Port_2_Input_ACK
  Router_9.io.Output_Port_4_Ready       :=    Router_5.io.Input_Port_2_Input_Ready     
              
  //Router 6 Port 2 OUT - Router 10 Port 4 IN
  Router_10.io.Input_Port_4_Data_Valid   :=    Router_6.io.Output_Port_2_Data_Valid
  Router_10.io.Input_Port_4_Input           :=    Router_6.io.Output_Port_2_Output
  Router_6.io.Output_Port_2_ACK          :=    Router_10.io.Input_Port_4_Input_ACK
  Router_6.io.Output_Port_2_Ready       :=    Router_10.io.Input_Port_4_Input_Ready
  //Router 10 Port 4 OUT - Router 6 Port 2 IN
  Router_6.io.Input_Port_2_Data_Valid   :=    Router_10.io.Output_Port_4_Data_Valid
  Router_6.io.Input_Port_2_Input           :=    Router_10.io.Output_Port_4_Output
  Router_10.io.Output_Port_4_ACK          :=    Router_6.io.Input_Port_2_Input_ACK
  Router_10.io.Output_Port_4_Ready       :=    Router_6.io.Input_Port_2_Input_Ready     
              
  //Router 7 Port 2 OUT - Router 11 Port 4 IN
  Router_11.io.Input_Port_4_Data_Valid   :=    Router_7.io.Output_Port_2_Data_Valid
  Router_11.io.Input_Port_4_Input           :=    Router_7.io.Output_Port_2_Output
  Router_7.io.Output_Port_2_ACK          :=    Router_11.io.Input_Port_4_Input_ACK
  Router_7.io.Output_Port_2_Ready       :=    Router_11.io.Input_Port_4_Input_Ready
  //Router 11 Port 4 OUT - Router 7 Port 2 IN
  Router_7.io.Input_Port_2_Data_Valid   :=    Router_11.io.Output_Port_4_Data_Valid
  Router_7.io.Input_Port_2_Input           :=    Router_11.io.Output_Port_4_Output
  Router_11.io.Output_Port_4_ACK          :=    Router_7.io.Input_Port_2_Input_ACK
  Router_11.io.Output_Port_4_Ready       :=    Router_7.io.Input_Port_2_Input_Ready     
                
  //Router 8 Port 2 OUT - Router 12 Port 4 IN
  Router_12.io.Input_Port_4_Data_Valid   :=    Router_8.io.Output_Port_2_Data_Valid
  Router_12.io.Input_Port_4_Input           :=    Router_8.io.Output_Port_2_Output
  Router_8.io.Output_Port_2_ACK          :=    Router_12.io.Input_Port_4_Input_ACK
  Router_8.io.Output_Port_2_Ready       :=    Router_12.io.Input_Port_4_Input_Ready
  //Router 12 Port 4 OUT - Router 8 Port 2 IN
  Router_8.io.Input_Port_2_Data_Valid   :=    Router_12.io.Output_Port_4_Data_Valid
  Router_8.io.Input_Port_2_Input           :=    Router_12.io.Output_Port_4_Output
  Router_12.io.Output_Port_4_ACK          :=    Router_8.io.Input_Port_2_Input_ACK
  Router_12.io.Output_Port_4_Ready       :=    Router_8.io.Input_Port_2_Input_Ready     
                  
  //Router 9 Port 2 OUT - Router 13 Port 4 IN
  Router_13.io.Input_Port_4_Data_Valid   :=    Router_9.io.Output_Port_2_Data_Valid
  Router_13.io.Input_Port_4_Input           :=    Router_9.io.Output_Port_2_Output
  Router_9.io.Output_Port_2_ACK          :=    Router_13.io.Input_Port_4_Input_ACK
  Router_9.io.Output_Port_2_Ready       :=    Router_13.io.Input_Port_4_Input_Ready
  //Router 13 Port 4 OUT - Router 9 Port 2 IN
  Router_9.io.Input_Port_2_Data_Valid   :=    Router_13.io.Output_Port_4_Data_Valid
  Router_9.io.Input_Port_2_Input           :=    Router_13.io.Output_Port_4_Output
  Router_12.io.Output_Port_4_ACK          :=    Router_9.io.Input_Port_2_Input_ACK
  Router_12.io.Output_Port_4_Ready       :=    Router_9.io.Input_Port_2_Input_Ready     
                    
  //Router 10 Port 2 OUT - Router 14 Port 4 IN
  Router_14.io.Input_Port_4_Data_Valid   :=    Router_10.io.Output_Port_2_Data_Valid
  Router_14.io.Input_Port_4_Input           :=    Router_10.io.Output_Port_2_Output
  Router_10.io.Output_Port_2_ACK          :=    Router_14.io.Input_Port_4_Input_ACK
  Router_10.io.Output_Port_2_Ready       :=    Router_14.io.Input_Port_4_Input_Ready
  //Router 14 Port 4 OUT - Router 10 Port 2 IN
  Router_10.io.Input_Port_2_Data_Valid   :=    Router_14.io.Output_Port_4_Data_Valid
  Router_10.io.Input_Port_2_Input           :=    Router_14.io.Output_Port_4_Output
  Router_14.io.Output_Port_4_ACK          :=    Router_10.io.Input_Port_2_Input_ACK
  Router_14.io.Output_Port_4_Ready       :=    Router_10.io.Input_Port_2_Input_Ready     
                      
  //Router 11 Port 2 OUT - Router 15 Port 4 IN
  Router_15.io.Input_Port_4_Data_Valid   :=    Router_11.io.Output_Port_2_Data_Valid
  Router_15.io.Input_Port_4_Input           :=    Router_11.io.Output_Port_2_Output
  Router_11.io.Output_Port_2_ACK          :=    Router_15.io.Input_Port_4_Input_ACK
  Router_11.io.Output_Port_2_Ready       :=    Router_15.io.Input_Port_4_Input_Ready
  //Router 15 Port 4 OUT - Router 11 Port 2 IN
  Router_11.io.Input_Port_2_Data_Valid   :=    Router_15.io.Output_Port_4_Data_Valid
  Router_11.io.Input_Port_2_Input           :=    Router_15.io.Output_Port_4_Output
  Router_15.io.Output_Port_4_ACK          :=    Router_11.io.Input_Port_2_Input_ACK
  Router_15.io.Output_Port_4_Ready       :=    Router_11.io.Input_Port_2_Input_Ready     
   
  
   /*Test assignments*/
  NetworkInterface_4.io.Input_Processor :=  io.Input_Processor_4
  NetworkInterface_4.io.Destination_Addr :=  io.Destination_Address_Processor_4
  NetworkInterface_4.io.Input_Valid_Processor :=  io.Input_Valid_Processor_4
  
  NetworkInterface_5.io.Input_Processor :=  io.Input_Processor_5
  NetworkInterface_5.io.Destination_Addr :=  io.Destination_Address_Processor_5
  NetworkInterface_5.io.Input_Valid_Processor :=  io.Input_Valid_Processor_5
  
  NetworkInterface_12.io.Input_Processor :=  io.Input_Processor_12
  NetworkInterface_12.io.Destination_Addr :=  io.Destination_Address_Processor_12
  NetworkInterface_12.io.Input_Valid_Processor :=  io.Input_Valid_Processor_12
}

class Network_on_Chip_tests(c: Network_on_Chip) extends Tester(c) {
 
    poke(c.io.Input_Valid_Processor_5, 1)
    poke(c.io.Input_Processor_5, 67305985)  // Data Flit 1 = 1, Data Flit 2 = 2, Data Flit 3 = 3, Data Flit 4 = 4 
    poke(c.io.Destination_Address_Processor_5, 3)
    
    poke(c.io.Input_Valid_Processor_4, 1)
    poke(c.io.Input_Processor_4, 0xDDCCBBAA)  
    poke(c.io.Destination_Address_Processor_4, 13)
    
    poke(c.io.Input_Valid_Processor_12, 1)
    poke(c.io.Input_Processor_12, 0x0605) 
    poke(c.io.Destination_Address_Processor_12, 6)

    step(2)
    poke(c.io.Input_Valid_Processor_5,0)
    poke(c.io.Input_Valid_Processor_4,0)
    poke(c.io.Input_Valid_Processor_12,0)
    
    step(1)
    step(80)
 
}

object Network_on_Chip {
  def main(args: Array[String]): Unit = {
    val tutArgs = args.slice(1, args.length)
    chiselMainTest(tutArgs, () => Module(new Network_on_Chip())) {
      c => new Network_on_Chip_tests(c)
    }
  }
}
