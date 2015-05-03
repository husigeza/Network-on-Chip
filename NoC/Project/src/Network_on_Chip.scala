

import Chisel._
import NetworkInterface._
import Router._


/**
 * @author patmos
 */
class Network_on_Chip extends Module {
    val io = new Bundle {

        val Input_Processor = UInt(INPUT, width = 32)
        val Input_Valid_Processor = Bool(INPUT)
        val Destination_Addr = UInt(INPUT, width = 4)
        
        val Output_Processor_9 = UInt(OUTPUT, width = 32)
        val Output_Processor_3 = UInt(OUTPUT, width = 32)

        val Network_Interface_Output = UInt(OUTPUT, width = 8)
        val Network_Interface_Data_Valid = Bool(OUTPUT)
        
        val Network_Interface_8_Output = UInt(OUTPUT, width = 8)
        val Network_Interface_Data_8_Valid = Bool(OUTPUT)
        val Router_8_Router_ACK_to_NI = Bool(OUTPUT)
        val Router_8_Router_Ready_to_NI = Bool(OUTPUT)

        val Data_Valid_to_Router_8_from_NI = Bool(OUTPUT)
        val Roter_8_Crossbar_ready = Bool(OUTPUT)
        val Roter_8_Input_Processor_Data_Valid_to_Crossbar = Bool(OUTPUT)
        
         val Router_8_Shift_reg_test = UInt(OUTPUT, width = 32)
         val Router_8_PortP_enable = Bool(OUTPUT)       
        val Router_8_Port_3_Output_ready_to_Crossbar = Bool(OUTPUT)
      /*  val Packet_ready = Bool(OUTPUT)
        val Data_Flit_Counter_test = UInt(OUTPUT, width = 3)
        val Input_Channel_Header_Flit = UInt(OUTPUT, width = 8)
        val Input_Channel_Data_Flit = UInt(OUTPUT, width = 8)
        

        
        val Router_1_Port_P_input_output_to_Crossbar = UInt(OUTPUT, width = 8)
        
        val Port_P_END = Bool(OUTPUT)
        val PortP_enable = Bool(OUTPUT)       
        val Port1_enable = Bool(OUTPUT)
        val Port_1_END = Bool(OUTPUT)
        val Processor_Header_arrived_test = UInt(OUTPUT, width = 1)
        val Router_1_Shift_reg_test = UInt(OUTPUT, width = 32)
        val Router_1_End_reg_test = UInt(OUTPUT)
        
        val Input_Processor_Input_Ready = Bool(OUTPUT)
    
        val Cross_bar_Port_3_Output_Router_1 = UInt(OUTPUT, width = 8)
        
        
        
        val Router_1_Port_3_Output = UInt(OUTPUT, width = 8)
        val Router_1_Port_3_Output_Valid = Bool(OUTPUT)
        val Router_2_Port_1_Input_Ready = Bool(OUTPUT)
        val Router_2_Port_1_Input_ACK = Bool(OUTPUT)*/
        
       
     val Router_8_X_reg_test = UInt(OUTPUT,width=3)
      val  Router_8_Y_reg_test = UInt(OUTPUT,width=3)
      
    val  Router_9_X_reg_test = UInt(OUTPUT,width=3)
    val  Router_9_Y_reg_test = UInt(OUTPUT,width=3)
      
    val Data_Valid_to_Router = Bool(OUTPUT)
    val InputBuffer_Data_0_test = UInt(OUTPUT, width = 32)
    val InputBuffer_Dest_0_test = UInt(OUTPUT, width = 4)
    val InputBuffer_Full_0_test = UInt(OUTPUT, width = 1)
    val InputBuffer_Data_1_test = UInt(OUTPUT, width = 32)
    val InputBuffer_Dest_1_test = UInt(OUTPUT, width = 4)
    val InputBuffer_Full_1_test = UInt(OUTPUT, width = 1)
    val Output_Header_Flit = UInt(OUTPUT, width = 8)
    val Output_Data_Flit_1 = UInt(OUTPUT, width = 8)
    val Output_Data_Flit_2 = UInt(OUTPUT, width = 8)
    val Output_Data_Flit_3 = UInt(OUTPUT, width = 8)
    val Output_Data_Flit_4 = UInt(OUTPUT, width = 8)
    val Packet_ready = Bool(OUTPUT)
    val Flits_Sent = Bool(OUTPUT)
    val Data_Flit_Counter_test = UInt(OUTPUT,width=3)    
    val Output_Buffers_ready_test = UInt(OUTPUT, width = 1)
    val InputBuffer_Selector_test = Bool(OUTPUT)
    
    val Router_8_Port_P_Flits_transmitted_test = UInt(OUTPUT,width=3)
     val Router_8_Port_P_Transmission_ongoing_test = UInt(OUTPUT,width=1)
      val Router_8_Processor_Header_arrived_test = UInt(OUTPUT,width=1)
        
        
        val Router_8_Port_3_Output         = UInt(OUTPUT, width = 8)
        val Router_8_Port_3_Output_Valid   = Bool(OUTPUT)
        val Router_9_Port_1_Input_Ready     = Bool(OUTPUT)
        val Router_9_Port_1_Input_ACK       = Bool(OUTPUT)   
        
        val Router_9_Port_3_Output = UInt(OUTPUT, width = 8)
        val Router_9_Port_3_Output_Valid = Bool(OUTPUT)
        val Router_10_Port_1_Input_Ready = Bool(OUTPUT)
        val Router_10_Port_1_Input_ACK = Bool(OUTPUT)   
        
        val Router_10_Port_3_Output = UInt(OUTPUT, width = 8)
        val Router_10_Port_3_Output_Valid = Bool(OUTPUT)
        val Router_11_Port_1_Input_Ready = Bool(OUTPUT)
        val Router_11_Port_1_Input_ACK = Bool(OUTPUT)   
        
        val Router_11_Port_4_Output = UInt(OUTPUT, width = 8)
        val Router_11_Port_4_Output_Valid = Bool(OUTPUT)
        val Router_7_Port_2_Input_Ready = Bool(OUTPUT)
        val Router_7_Port_2_Input_ACK = Bool(OUTPUT)   
        
        val Router_7_Port_4_Output = UInt(OUTPUT, width = 8)
        val Router_7_Port_4_Output_Valid = Bool(OUTPUT)
        val Router_3_Port_2_Input_Ready = Bool(OUTPUT)
        val Router_3_Port_2_Input_ACK = Bool(OUTPUT)   
        
        val Router_9_Port_P_Output = UInt(OUTPUT, width = 8)
        val Router_3_Port_P_Output = UInt(OUTPUT, width = 8)

    }

 
    
        val NetworkInterface_8 = Module(new NetworkInterface(8))
        val Router_8 = Module(new Router)
        val NetworkInterface_9 = Module(new NetworkInterface(9))
        val Router_9 = Module(new Router)
        val Router_10 = Module(new Router)
        val Router_11 = Module(new Router)
        val Router_7 = Module(new Router)
        val Router_3 = Module(new Router)
        val NetworkInterface_3 = Module(new NetworkInterface(3))
    
    //Router 8 - Network Interface 8
    //NI Output - Router Input
    Router_8.io.Input_Processor_Input       := NetworkInterface_8.io.Output
    Router_8.io.Input_Processor_Data_Valid  := NetworkInterface_8.io.Output_Data_Valid
    NetworkInterface_8.io.Input_ACK         := Router_8.io.Input_Processor_Input_ACK
    NetworkInterface_8.io.Input_Ready       := Router_8.io.Input_Processor_Input_Ready
    //NI Input - Router Output
    NetworkInterface_8.io.Router_Input              := Router_8.io.Output_Port_P_Output
    NetworkInterface_8.io.Router_Input_Data_Valid   := Router_8.io.Output_Port_P_Data_Valid
    Router_8.io.Output_Port_P_ACK                   := NetworkInterface_8.io.Router_Input_ACK
    Router_8.io.Output_Port_P_Ready                 := NetworkInterface_8.io.Router_Input_Ready
    
    //Router 9 - Network Interface 9
    //NI Output - Router Input
    Router_9.io.Input_Processor_Input       := NetworkInterface_9.io.Output
    Router_9.io.Input_Processor_Data_Valid  := NetworkInterface_9.io.Output_Data_Valid
    NetworkInterface_9.io.Input_ACK         := Router_9.io.Input_Processor_Input_ACK
    NetworkInterface_9.io.Input_Ready       := Router_9.io.Input_Processor_Input_Ready
    //NI Input - Router Output
    NetworkInterface_9.io.Router_Input              := Router_9.io.Output_Port_P_Output
    NetworkInterface_9.io.Router_Input_Data_Valid   := Router_9.io.Output_Port_P_Data_Valid
    Router_9.io.Output_Port_P_ACK                   := NetworkInterface_9.io.Router_Input_ACK
    Router_9.io.Output_Port_P_Ready                 := NetworkInterface_9.io.Router_Input_Ready
    
    //Router 3 - Network Interface 3
    //NI Output - Router Input
    Router_3.io.Input_Processor_Input       := NetworkInterface_3.io.Output
    Router_3.io.Input_Processor_Data_Valid  := NetworkInterface_3.io.Output_Data_Valid
    NetworkInterface_3.io.Input_ACK         := Router_3.io.Input_Processor_Input_ACK
    NetworkInterface_3.io.Input_Ready       := Router_3.io.Input_Processor_Input_Ready
    //NI Input - Router Output
    NetworkInterface_3.io.Router_Input              := Router_3.io.Output_Port_P_Output
    NetworkInterface_3.io.Router_Input_Data_Valid   := Router_3.io.Output_Port_P_Data_Valid
    Router_3.io.Output_Port_P_ACK                   := NetworkInterface_3.io.Router_Input_ACK
    Router_3.io.Output_Port_P_Ready                 := NetworkInterface_3.io.Router_Input_Ready
    
      
    //Router 8 Port 3 OUT - Router 9 Port 1 IN
    Router_9.io.Input_Port_1_Data_Valid   :=   Router_8.io.Output_Port_3_Data_Valid
    Router_9.io.Input_Port_1_Input        :=   Router_8.io.Output_Port_3_Output
    Router_8.io.Output_Port_3_ACK         :=   Router_9.io.Input_Port_1_Input_ACK
    Router_8.io.Output_Port_3_Ready       :=   Router_9.io.Input_Port_1_Input_Ready     
    
    //Router 9 Port 3 OUT - Router 10 Port 1 IN
    Router_10.io.Input_Port_1_Data_Valid   :=   Router_9.io.Output_Port_3_Data_Valid
    Router_10.io.Input_Port_1_Input        :=   Router_9.io.Output_Port_3_Output
    Router_9.io.Output_Port_3_ACK         :=   Router_10.io.Input_Port_1_Input_ACK
    Router_9.io.Output_Port_3_Ready       :=   Router_10.io.Input_Port_1_Input_Ready     
    
    //Router 10 Port 3 OUT - Router 11 Port 1 IN
    Router_11.io.Input_Port_1_Data_Valid   :=   Router_10.io.Output_Port_3_Data_Valid
    Router_11.io.Input_Port_1_Input        :=   Router_10.io.Output_Port_3_Output
    Router_10.io.Output_Port_3_ACK         :=   Router_11.io.Input_Port_1_Input_ACK
    Router_10.io.Output_Port_3_Ready       :=   Router_11.io.Input_Port_1_Input_Ready  
    
    //Router 11 Port 4 OUT - Router 7 Port 2 IN
    Router_7.io.Input_Port_2_Data_Valid   :=    Router_11.io.Output_Port_4_Data_Valid
    Router_7.io.Input_Port_2_Input        :=    Router_11.io.Output_Port_4_Output
    Router_11.io.Output_Port_4_ACK         :=   Router_7.io.Input_Port_2_Input_ACK
    Router_11.io.Output_Port_4_Ready       :=   Router_7.io.Input_Port_2_Input_Ready  
        
    //Router 7 Port 4 OUT - Router 3 Port 2 IN
    Router_3.io.Input_Port_2_Data_Valid   :=   Router_7.io.Output_Port_4_Data_Valid
    Router_3.io.Input_Port_2_Input        :=   Router_7.io.Output_Port_4_Output
    Router_7.io.Output_Port_4_ACK         :=   Router_3.io.Input_Port_2_Input_ACK
    Router_7.io.Output_Port_4_Ready       :=   Router_3.io.Input_Port_2_Input_Ready  

   /* Router_1.io.Input_Processor_Input := NetworkInterface_1.io.Output
    Router_1.io.Input_Processor_Data_Valid := NetworkInterface_1.io.Output_Data_Valid
    NetworkInterface_1.io.Input_ACK := Router_1.io.Input_Processor_Input_ACK
    NetworkInterface_1.io.Input_Ready := Router_1.io.Input_Processor_Input_Ready

    Router_2.io.Input_Port_1_Data_Valid := Router_1.io.Output_Port_3_Data_Valid
    Router_2.io.Input_Port_1_Input := Router_1.io.Output_Port_3_Output
    Router_1.io.Output_Port_3_ACK := Router_2.io.Input_Port_1_Input_ACK
    Router_1.io.Output_Port_3_Ready := Router_2.io.Input_Port_1_Input_Ready*/

    //*TESTING*/
    /*NetworkInterface_1.io.Input_Processor := io.Input_Processor
    NetworkInterface_1.io.Input_Valid_Processor := io.Input_Valid_Processor
    NetworkInterface_1.io.Destination_Addr := io.Destination_Addr
    io.Network_Interface_Output := NetworkInterface_1.io.Output
    io.Network_Interface_Data_Valid := NetworkInterface_1.io.Output_Data_Valid
    io.Router_ACK := Router_1.io.Input_Processor_Input_ACK
    io.Router_Ready := Router_1.io.Input_Processor_Input_Ready
    io.Packet_ready := NetworkInterface_1.io.Packet_ready
    io.Data_Flit_Counter_test := NetworkInterface_1.io.Data_Flit_Counter_test
    io.Input_Channel_Header_Flit := Router_1.io.Input_Channel_Header_Flit
    io.Input_Channel_Data_Flit := Router_1.io.Input_Channel_Data_Flit

    
    io.Router_1_Port_P_input_output_to_Crossbar := Router_1.io.Port_P_input_output_to_crossbar
    io.Roter_1_Crossbar_ready := Router_1.io.Crossbar_ready
    io.Port_P_END := Router_1.io.Port_P_END
    io.Port_1_END := Router_1.io.Port_1_END
    
    io.Port1_enable := Router_1.io.Port1_enable
    io.PortP_enable := Router_1.io.PortP_enable
    io.Processor_Header_arrived_test := Router_1.io.Processor_Header_arrived_test
    io.Router_1_Shift_reg_test := Router_1.io.Shift_reg_test
    io.Router_1_End_reg_test := Router_1.io.End_reg_test
    io.Input_Processor_Input_Ready := Router_1.io.Input_Processor_Input_Ready
    io.Input_Processor_Data_Valid_to_Crossbar := Router_1.io.Processor_Data_Valid_to_Crossbar
    io.Cross_bar_Port_3_Output_Router_1 := Router_1.io.Cross_bar_Port_3_Output
    io.Router_1_X_reg_test := Router_1.io.X_reg_test
    io.Router_1_Y_reg_test := Router_1.io.Y_reg_test
    
    io.Router_1_Port_3_Output := Router_1.io.Output_Port_3_Output
    io.Router_1_Port_3_Output_Valid := Router_1.io.Output_Port_3_Data_Valid
    io.Router_2_Port_1_Input_Ready := Router_2.io.Input_Port_1_Input_Ready
    io.Router_2_Port_1_Input_ACK  := Router_2.io.Input_Port_1_Input_ACK*/
       
    
   
    io.Router_8_Port_3_Output_Valid  := Router_8.io.Output_Port_3_Data_Valid
    io.Router_9_Port_1_Input_Ready := Router_9.io.Input_Port_1_Input_Ready
    io.Router_9_Port_1_Input_ACK   := Router_9.io.Input_Port_1_Input_ACK
    
    NetworkInterface_8.io.Input_Processor := io.Input_Processor
    NetworkInterface_8.io.Input_Valid_Processor := io.Input_Valid_Processor
    NetworkInterface_8.io.Destination_Addr := io.Destination_Addr
    
    io.Router_8_Port_3_Output        :=   Router_8.io.Output_Port_3_Output
    io.Router_8_Port_3_Output_Valid  :=   Router_8.io.Output_Port_3_Data_Valid
    io.Router_9_Port_1_Input_Ready   :=   Router_9.io.Input_Port_1_Input_Ready
    io.Router_9_Port_1_Input_ACK     :=   Router_9.io.Input_Port_1_Input_ACK
                                                 
    io.Router_9_Port_3_Output        :=   Router_9.io.Output_Port_3_Output
    io.Router_9_Port_3_Output_Valid  :=   Router_9.io.Output_Port_3_Data_Valid
    io.Router_10_Port_1_Input_Ready  :=   Router_10.io.Input_Port_1_Input_Ready
    io.Router_10_Port_1_Input_ACK    :=   Router_10.io.Input_Port_1_Input_ACK
                                                 
    io.Router_10_Port_3_Output       :=   Router_10.io.Output_Port_3_Output
    io.Router_10_Port_3_Output_Valid :=   Router_10.io.Output_Port_3_Data_Valid
    io.Router_11_Port_1_Input_Ready  :=   Router_11.io.Input_Port_1_Input_Ready
    io.Router_11_Port_1_Input_ACK    :=   Router_11.io.Input_Port_1_Input_ACK
                                            
    io.Router_11_Port_4_Output       :=   Router_11.io.Output_Port_4_Output
    io.Router_11_Port_4_Output_Valid :=   Router_11.io.Output_Port_4_Data_Valid
    io.Router_7_Port_2_Input_Ready   :=   Router_7.io.Input_Port_2_Input_Ready
    io.Router_7_Port_2_Input_ACK     :=   Router_7.io.Input_Port_2_Input_ACK
                                               
    io.Router_7_Port_4_Output        :=   Router_7.io.Output_Port_4_Output
    io.Router_7_Port_4_Output_Valid  :=   Router_7.io.Output_Port_4_Data_Valid
    io.Router_3_Port_2_Input_Ready   :=   Router_3.io.Input_Port_2_Input_Ready
    io.Router_3_Port_2_Input_ACK     :=   Router_3.io.Input_Port_2_Input_ACK

    io.Output_Processor_9 := NetworkInterface_9.io.Output_Processor
    io.Router_9_Port_P_Output := Router_9.io.Output_Port_P_Output
    
    
    io.Output_Processor_3 := NetworkInterface_3.io.Output_Processor
    io.Router_3_Port_P_Output := Router_3.io.Output_Port_P_Output

  io.InputBuffer_Data_0_test       := NetworkInterface_8.io.InputBuffer_Data_0_test
  io.InputBuffer_Dest_0_test       := NetworkInterface_8.io.InputBuffer_Dest_0_test
  io.InputBuffer_Full_0_test       := NetworkInterface_8.io.InputBuffer_Full_0_test
  io.InputBuffer_Data_1_test         := NetworkInterface_8.io.InputBuffer_Data_1_test
  io.InputBuffer_Dest_1_test       := NetworkInterface_8.io.InputBuffer_Dest_1_test
  io.InputBuffer_Full_1_test       := NetworkInterface_8.io.InputBuffer_Full_1_test
  io.Output_Header_Flit           := NetworkInterface_8.io.Output_Header_Flit
  io.Output_Data_Flit_1             := NetworkInterface_8.io.Output_Data_Flit_1
  io.Output_Data_Flit_2               := NetworkInterface_8.io.Output_Data_Flit_2
  io.Output_Data_Flit_3             := NetworkInterface_8.io.Output_Data_Flit_3
  io.Output_Data_Flit_4             := NetworkInterface_8.io.Output_Data_Flit_4
  io.Packet_ready                   := NetworkInterface_8.io.Packet_ready
  io.Flits_Sent                     := NetworkInterface_8.io.Flits_Sent    
  io.Data_Flit_Counter_test         := NetworkInterface_8.io.Data_Flit_Counter_test 
  io.Output_Buffers_ready_test       := NetworkInterface_8.io.Output_Buffers_ready_test 
  io.InputBuffer_Selector_test       := NetworkInterface_8.io.InputBuffer_Selector_test
     

       
  io.Roter_8_Crossbar_ready                         := Router_8.io.Crossbar_ready   
  io.Roter_8_Input_Processor_Data_Valid_to_Crossbar := Router_8.io.Processor_Data_Valid_to_Crossbar
  io.Router_8_PortP_enable := Router_8.io.PortP_enable
  io.Router_8_Shift_reg_test := Router_8.io.Shift_reg_test
  io.Router_8_Port_3_Output_ready_to_Crossbar := Router_8.io.Port_3_Output_ready_to_Crossbar
  
  io.Router_8_Port_P_Flits_transmitted_test := Router_8.io.Port_P_Flits_transmitted_test
  io.Router_8_Port_P_Transmission_ongoing_test := Router_8.io.Port_P_Transmission_ongoing_test
  io.Router_8_Processor_Header_arrived_test := Router_8.io.Processor_Header_arrived_test
  
  io.Router_8_X_reg_test := Router_8.io.X_reg_test
  io.Router_8_Y_reg_test := Router_8.io.Y_reg_test
  
    io.Network_Interface_8_Output := NetworkInterface_8.io.Output
    io.Router_8_Router_ACK_to_NI := Router_8.io.Input_Processor_Input_ACK
    io.Router_8_Router_Ready_to_NI := Router_8.io.Input_Processor_Input_Ready
    io.Data_Valid_to_Router_8_from_NI := NetworkInterface_8.io.Data_Valid_to_Router
    
  io.Router_9_X_reg_test := Router_9.io.X_reg_test
  io.Router_9_Y_reg_test := Router_9.io.Y_reg_test
  
}

class Network_on_Chip_tests(c : Network_on_Chip) extends Tester(c) {
 
   	poke(c.io.Input_Valid_Processor, 1)
    poke(c.io.Input_Processor, 0x4030201)
    poke(c.io.Destination_Addr, 3)
    
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
      
    step(1)
	  poke(c.io.Input_Valid_Processor, 0)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
    
    step(1)
    poke(c.io.Input_Valid_Processor, 1)
    poke(c.io.Input_Processor, 0xDDCCBBAA)
    poke(c.io.Destination_Addr, 3)
    
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
    
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
    
         
    step(1)
 	  poke(c.io.Input_Valid_Processor, 0)  

    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
     
    step(1)
    peek(c.io.Network_Interface_Output)
    peek(c.io.Router_8_Port_3_Output) 
    peek(c.io.Router_9_Port_3_Output) 
    peek(c.io.Router_10_Port_3_Output)  
    peek(c.io.Router_11_Port_4_Output)  
    peek(c.io.Router_7_Port_4_Output) 
    peek(c.io.Router_3_Port_P_Output)
    peek(c.io.Output_Processor_3)
    
    
   
   
}

object Network_on_Chip {    
  def main(args: Array[String]): Unit = {
   val tutArgs = args.slice(1, args.length)
    chiselMainTest(tutArgs, () => Module(new Network_on_Chip())) {
      c => new Network_on_Chip_tests(c) }
    }
  }