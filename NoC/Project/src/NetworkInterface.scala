import Chisel._


/**
 * @author patmos
 */

class NetworkInterface_Input_Port_Processor extends Module {
  val io = new Bundle {
    //IOs for Data FROM Processor
    val Input_Processor = UInt(INPUT, width = 32)
    val Input_Valid_Processor = Bool(INPUT)
    val Data_ACK_Processor = Bool(OUTPUT)
    val Destination_Addr = UInt(INPUT, width = 4)
    val Input_Buffer_Full_from_Processor = Bool(OUTPUT)

    // IOs to and from Packet Encapsulation unit
    val InputBuffer_Data_0 = UInt(OUTPUT, width = 32)
    val InputBuffer_Dest_0 = UInt(OUTPUT, width = 4)
    val InputBuffer_Full_0 = UInt(OUTPUT, width = 1)
    val InputBuffer_Data_1 = UInt(OUTPUT, width = 32)
    val InputBuffer_Dest_1 = UInt(OUTPUT, width = 4)
    val InputBuffer_Full_1 = UInt(OUTPUT, width = 1)
    val InputBuffer_Full_0_Empty = Bool(INPUT)
    val InputBuffer_Full_1_Empty = Bool(INPUT)
  }

  /*Input buffers*/
  val InputBuffer_Data_0 = Reg(UInt(width = 32))
  val InputBuffer_Dest_0 = Reg(UInt(width = 4))
  val InputBuffer_Full_0 = Reg(UInt(), init = UInt(0))
  val InputBuffer_Data_1 = Reg(UInt(width = 32))
  val InputBuffer_Dest_1 = Reg(UInt(width = 4))
  val InputBuffer_Full_1 = Reg(UInt(), init = UInt(0))
  /****************************************************/
  io.InputBuffer_Data_0 := InputBuffer_Data_0
  io.InputBuffer_Dest_0 := InputBuffer_Dest_0
  io.InputBuffer_Full_0 := InputBuffer_Full_0
  io.InputBuffer_Data_1 := InputBuffer_Data_1
  io.InputBuffer_Dest_1 := InputBuffer_Dest_1
  io.InputBuffer_Full_1 := InputBuffer_Full_1

  /*Input buffer selector*/
  //val InputBuffer_Selector = Reg(UInt(), init = UInt(0))
  /**********************************************************/
  //io.InputBuffer_Selector := InputBuffer_Selector

  // Register to sync the ACK value with DATA 
  val Data_ACK_Processor = Reg(Bool(), init = UInt(0))
  io.Data_ACK_Processor := Data_ACK_Processor
  /********************************************/

  // Get Data from Processor, put it into a free buffer
  when(io.Input_Valid_Processor && io.Data_ACK_Processor === UInt(0)) {
    when(InputBuffer_Full_0 === UInt(0)) {
      InputBuffer_Data_0 := io.Input_Processor
      InputBuffer_Dest_0 := io.Destination_Addr
      InputBuffer_Full_0 := UInt(1)
      Data_ACK_Processor := UInt(1)
    }
      .elsewhen(InputBuffer_Full_1 === UInt(0)) {
        InputBuffer_Data_1 := io.Input_Processor
        InputBuffer_Dest_1 := io.Destination_Addr
        InputBuffer_Full_1 := UInt(1)
        Data_ACK_Processor := UInt(1)
      }
  }.otherwise { // Keep ACK to Processor Low when it does not want to send data
    Data_ACK_Processor := UInt(0)
  }

  // Decide from where to get the data
  /*when(InputBuffer_Full_0 === UInt(1) && InputBuffer_Full_1 === UInt(0)) {
    InputBuffer_Selector := UInt(1)
  }
    .elsewhen(InputBuffer_Full_0 === UInt(0) && InputBuffer_Full_1 === UInt(1)) {
      InputBuffer_Selector := UInt(2)
    }
    .otherwise {
      InputBuffer_Selector := InputBuffer_Selector
    }*/

  // If all input buffers are full, indicate this to the processor
  when(InputBuffer_Full_0 === UInt(1) && InputBuffer_Full_1 === UInt(1)) {
    io.Input_Buffer_Full_from_Processor := UInt(1)
  }
    .otherwise {
      io.Input_Buffer_Full_from_Processor := UInt(0)
    }

  when(io.InputBuffer_Full_0_Empty === UInt(1)) {
    InputBuffer_Full_0 := UInt(0)
  }
  when(io.InputBuffer_Full_1_Empty === UInt(1)) {
    InputBuffer_Full_1 := UInt(0)
  }

}

class NetworkInterface_Packet_Encapsulate(Current_Proc_Number: Int) extends Module {
  val io = new Bundle {

    //IOs to and from INPUT Port
    val InputBuffer_Data_0 = UInt(INPUT, width = 32)
    val InputBuffer_Dest_0 = UInt(INPUT, width = 4)
    val InputBuffer_Full_0 = UInt(INPUT, width = 1)
    val InputBuffer_Data_1 = UInt(INPUT, width = 32)
    val InputBuffer_Dest_1 = UInt(INPUT, width = 4)
    val InputBuffer_Full_1 = UInt(INPUT, width = 1)
    val InputBuffer_Full_0_Empty = Bool(OUTPUT)
    val InputBuffer_Full_1_Empty = Bool(OUTPUT)

    //IOs to and from OUT Port
    val Output_Header_Flit = UInt(OUTPUT, width = 8)
    val Output_Data_Flit_1 = UInt(OUTPUT, width = 8)
    val Output_Data_Flit_2 = UInt(OUTPUT, width = 8)
    val Output_Data_Flit_3 = UInt(OUTPUT, width = 8)
    val Output_Data_Flit_4 = UInt(OUTPUT, width = 8)

    val Packet_ready = Bool(OUTPUT)
    val Flits_Sent = Bool(INPUT)
    
    val Output_Buffers_ready_test = UInt(OUTPUT, width = 1)
    val InputBuffer_Selector_test = Bool(OUTPUT)
  }

  val Current_Proc_Num = UInt(Current_Proc_Number)

  /*NODE NUMBERS - COORDINATES*/
  val Mesh_topology_coordinates_x = Vec.fill(16) { UInt(width = 2) }
  val Mesh_topology_coordinates_y = Vec.fill(16) { UInt(width = 2) }
  Mesh_topology_coordinates_x(0) := UInt(0)
  Mesh_topology_coordinates_y(0) := UInt(0)
  Mesh_topology_coordinates_x(1) := UInt(1)
  Mesh_topology_coordinates_y(1) := UInt(0)
  Mesh_topology_coordinates_x(2) := UInt(2)
  Mesh_topology_coordinates_y(2) := UInt(0)
  Mesh_topology_coordinates_x(3) := UInt(3)
  Mesh_topology_coordinates_y(3) := UInt(0)
  Mesh_topology_coordinates_x(4) := UInt(0)
  Mesh_topology_coordinates_y(4) := UInt(1)
  Mesh_topology_coordinates_x(5) := UInt(1)
  Mesh_topology_coordinates_y(5) := UInt(1)
  Mesh_topology_coordinates_x(6) := UInt(2)
  Mesh_topology_coordinates_y(6) := UInt(1)
  Mesh_topology_coordinates_x(7) := UInt(3)
  Mesh_topology_coordinates_y(7) := UInt(1)
  Mesh_topology_coordinates_x(8) := UInt(0)
  Mesh_topology_coordinates_y(8) := UInt(2)
  Mesh_topology_coordinates_x(9) := UInt(1)
  Mesh_topology_coordinates_y(9) := UInt(2)
  Mesh_topology_coordinates_x(10) := UInt(2)
  Mesh_topology_coordinates_y(10) := UInt(2)
  Mesh_topology_coordinates_x(11) := UInt(3)
  Mesh_topology_coordinates_y(11) := UInt(2)
  Mesh_topology_coordinates_x(12) := UInt(0)
  Mesh_topology_coordinates_y(12) := UInt(3)
  Mesh_topology_coordinates_x(13) := UInt(1)
  Mesh_topology_coordinates_y(13) := UInt(3)
  Mesh_topology_coordinates_x(14) := UInt(2)
  Mesh_topology_coordinates_y(14) := UInt(3)
  Mesh_topology_coordinates_x(15) := UInt(3)
  Mesh_topology_coordinates_y(15) := UInt(3)
  /********************************************/

  /*Registers to store the Flits*/
  val Header_reg = Reg(UInt(width = 8))
  val Data_flit_1_reg = Reg(UInt(width = 8))
  val Data_flit_2_reg = Reg(UInt(width = 8))
  val Data_flit_3_reg = Reg(UInt(width = 8))
  val Data_flit_4_reg = Reg(UInt(width = 8))

  io.Output_Header_Flit := Header_reg
  io.Output_Data_Flit_1 := Data_flit_1_reg
  io.Output_Data_Flit_2 := Data_flit_2_reg
  io.Output_Data_Flit_3 := Data_flit_3_reg
  io.Output_Data_Flit_4 := Data_flit_4_reg
  /*******************************************/

  val Flits_Sent = Reg(Bool(), init = UInt(1))
  Flits_Sent := io.Flits_Sent

  /*Register which indicates the out Port can receive data*/
  val Output_Buffers_ready = Reg(UInt(), init = UInt(1))
  io.Output_Buffers_ready_test := Output_Buffers_ready
  

  val Packet_ready_reg = Reg(Bool(), init = UInt(0))
  io.Packet_ready := Packet_ready_reg

  
  /*Select the input buffer*/
  //val InputBuffer_Selector = Reg(UInt(), init = UInt(0))
  val InputBuffer_Selector = Bool()
  io.InputBuffer_Selector_test := InputBuffer_Selector
  
  when(io.InputBuffer_Full_0 === UInt(1) && io.InputBuffer_Full_1 === UInt(0)) {
    InputBuffer_Selector := UInt(0)
  }
    .elsewhen(io.InputBuffer_Full_0 === UInt(0) && io.InputBuffer_Full_1 === UInt(1)) {
      InputBuffer_Selector := UInt(1)
    }
    .otherwise {
      InputBuffer_Selector := UInt(0)
    }
  
  
  //According to Round Robin grab the data from the input buffers, create the header and the data flits
  when(Output_Buffers_ready === UInt(1)) {
    // INPUT BUFFER 0 is selected
    when(InputBuffer_Selector === UInt(0) && io.InputBuffer_Full_0 === UInt(1)) {  //io.InputBuffer_Full_0 === UInt(1) necessary because of the first cycle
      // Create header
      // X coordinate
      when((Mesh_topology_coordinates_x(io.InputBuffer_Dest_0) - Mesh_topology_coordinates_x(Current_Proc_Num)) > UInt(0)) {
        Header_reg(7) := UInt(0)
        Header_reg(6, 5) := (Mesh_topology_coordinates_x(io.InputBuffer_Dest_0) - Mesh_topology_coordinates_x(Current_Proc_Num))
      }
        .elsewhen((Mesh_topology_coordinates_x(io.InputBuffer_Dest_0) - Mesh_topology_coordinates_x(Current_Proc_Num)) < UInt(0)) {
          Header_reg(7) := UInt(1)
          Header_reg(6, 5) := (Mesh_topology_coordinates_x(Current_Proc_Num) - Mesh_topology_coordinates_x(io.InputBuffer_Dest_0))
        }
        .otherwise {
          Header_reg(7, 5) := UInt(0)
        }
      // Y coordinate
      when((Mesh_topology_coordinates_y(io.InputBuffer_Dest_0) - Mesh_topology_coordinates_y(Current_Proc_Num)) > UInt(0)) {
        Header_reg(4) := UInt(0)
        Header_reg(3, 2) := (Mesh_topology_coordinates_y(io.InputBuffer_Dest_0) - Mesh_topology_coordinates_y(Current_Proc_Num))
      }
        .elsewhen((Mesh_topology_coordinates_y(io.InputBuffer_Dest_0) - Mesh_topology_coordinates_y(Current_Proc_Num)) < UInt(0)) {
          Header_reg(4) := UInt(1)
          Header_reg(3, 2) := (Mesh_topology_coordinates_y(Current_Proc_Num) - Mesh_topology_coordinates_y(io.InputBuffer_Dest_0))
        }
        .otherwise {
          Header_reg(4, 2) := UInt(0)
        }
      // Create Data flits (only necessary number of flits) and set the flit number value in the header
      when(io.InputBuffer_Data_0(31, 0) === UInt(0)) {
        Header_reg(1, 0) := UInt(0)
        Data_flit_1_reg := UInt(0)
      }
        .elsewhen(io.InputBuffer_Data_0(31, 8) === UInt(0)) {
          Header_reg(1, 0) := UInt(0)
          Data_flit_1_reg := io.InputBuffer_Data_0(7, 0)
        }
        .elsewhen(io.InputBuffer_Data_0(31, 16) === UInt(0)) {
          Header_reg(1, 0) := UInt(1)
          Data_flit_1_reg := io.InputBuffer_Data_0(7, 0)
          Data_flit_2_reg := io.InputBuffer_Data_0(15, 8)
        }
        .elsewhen(io.InputBuffer_Data_0(31, 24) === UInt(0)) {
          Header_reg(1, 0) := UInt(2)
          Data_flit_1_reg := io.InputBuffer_Data_0(7, 0)
          Data_flit_2_reg := io.InputBuffer_Data_0(15, 8)
          Data_flit_3_reg := io.InputBuffer_Data_0(23, 16)
        }.otherwise {
          Header_reg(1, 0) := UInt(3)
          Data_flit_1_reg := io.InputBuffer_Data_0(7, 0)
          Data_flit_2_reg := io.InputBuffer_Data_0(15, 8)
          Data_flit_3_reg := io.InputBuffer_Data_0(23, 16)
          Data_flit_4_reg := io.InputBuffer_Data_0(31, 24)
        }
      //Indicate to the output port that the packet is ready
      Packet_ready_reg := UInt(1)
      Output_Buffers_ready := UInt(0)
      io.InputBuffer_Full_0_Empty := UInt(1)
    }
      //Input Buffer 1
      .elsewhen(InputBuffer_Selector === UInt(1) && io.InputBuffer_Full_1 === UInt(1)) {
        // Create header
        // X coordinate
        when((Mesh_topology_coordinates_x(io.InputBuffer_Dest_1) - Mesh_topology_coordinates_x(Current_Proc_Num)) > UInt(0)) {
          Header_reg(7) := UInt(0)
          Header_reg(6, 5) := (Mesh_topology_coordinates_x(io.InputBuffer_Dest_1) - Mesh_topology_coordinates_x(Current_Proc_Num))
        }
          .elsewhen((Mesh_topology_coordinates_x(io.InputBuffer_Dest_1) - Mesh_topology_coordinates_x(Current_Proc_Num)) < UInt(0)) {
            Header_reg(7) := UInt(1)
            Header_reg(6, 5) := (Mesh_topology_coordinates_x(Current_Proc_Num) - Mesh_topology_coordinates_x(io.InputBuffer_Dest_1))
          }
          .otherwise {
            Header_reg(7, 5) := UInt(0)
          }
        // Y coordinate
        when((Mesh_topology_coordinates_y(io.InputBuffer_Dest_1) - Mesh_topology_coordinates_y(Current_Proc_Num)) > UInt(0)) {
          Header_reg(4) := UInt(1)
          Header_reg(3, 2) := (Mesh_topology_coordinates_y(io.InputBuffer_Dest_1) - Mesh_topology_coordinates_y(Current_Proc_Num))
        }
          .elsewhen((Mesh_topology_coordinates_y(io.InputBuffer_Dest_1) - Mesh_topology_coordinates_y(Current_Proc_Num)) < UInt(0)) {
            Header_reg(4) := UInt(0)
            Header_reg(3, 2) := (Mesh_topology_coordinates_y(Current_Proc_Num) - Mesh_topology_coordinates_y(io.InputBuffer_Dest_1))
          }
          .otherwise {
            Header_reg(4, 2) := UInt(0)
          }
        // Create Data flits (only necessary number of flits) and set the flit number value in the header
        when(io.InputBuffer_Data_1(31, 0) === UInt(0)) {
          Header_reg(1, 0) := UInt(0)
          Data_flit_1_reg := UInt(0)
        }
          .elsewhen(io.InputBuffer_Data_1(31, 8) === UInt(0)) {
            Header_reg(1, 0) := UInt(0)
            Data_flit_1_reg := io.InputBuffer_Data_1(7, 0)
          }
          .elsewhen(io.InputBuffer_Data_1(31, 16) === UInt(0)) {
            Header_reg(1, 0) := UInt(1)
            Data_flit_1_reg := io.InputBuffer_Data_1(7, 0)
            Data_flit_2_reg := io.InputBuffer_Data_1(15, 8)
          }
          .elsewhen(io.InputBuffer_Data_1(31, 24) === UInt(0)) {
            Header_reg(1, 0) := UInt(2)
            Data_flit_1_reg := io.InputBuffer_Data_1(7, 0)
            Data_flit_2_reg := io.InputBuffer_Data_1(15, 8)
            Data_flit_3_reg := io.InputBuffer_Data_1(23, 16)
          }.otherwise {
            Header_reg(1, 0) := UInt(3)
            Data_flit_1_reg := io.InputBuffer_Data_1(7, 0)
            Data_flit_2_reg := io.InputBuffer_Data_1(15, 8)
            Data_flit_3_reg := io.InputBuffer_Data_1(23, 16)
            Data_flit_4_reg := io.InputBuffer_Data_1(31, 24)
          }
        //Indicate to the output port that the packet is ready
        Packet_ready_reg := UInt(1)
        Output_Buffers_ready := UInt(0)
        io.InputBuffer_Full_1_Empty := UInt(1)
      }
      .otherwise {
        io.InputBuffer_Full_0_Empty := UInt(0)
        io.InputBuffer_Full_1_Empty := UInt(0)
      }
  }
  .otherwise {
    io.InputBuffer_Full_0_Empty := UInt(0)
    io.InputBuffer_Full_1_Empty := UInt(0)
    }

  when(io.Flits_Sent === UInt(1)) {
    Output_Buffers_ready := UInt(1)
    Packet_ready_reg := UInt(0)
    Header_reg := UInt(0)
    Data_flit_1_reg := UInt(0)
    Data_flit_2_reg := UInt(0)
    Data_flit_3_reg := UInt(0)
    Data_flit_4_reg := UInt(0)
  }
}



class NetworkInterface_Port_Output extends Module {
  val io = new Bundle {
    
    /*IOs to and from Router*/
    val Output = UInt(OUTPUT, width = 8)
    val Output_Data_Valid = Bool(OUTPUT)   
    val Input_Ready = Bool(INPUT)
    val Input_ACK = Bool(INPUT)
    
    val Flits_Sent = Bool(OUTPUT)

    val Output_Header_Flit = UInt(INPUT, width = 8)
    val Output_Data_Flit_1 = UInt(INPUT, width = 8)
    val Output_Data_Flit_2 = UInt(INPUT, width = 8)
    val Output_Data_Flit_3 = UInt(INPUT, width = 8)
    val Output_Data_Flit_4 = UInt(INPUT, width = 8)
    val Packet_ready = Bool(INPUT)
    
    val Data_Flit_Counter_test = UInt(OUTPUT,width=3)
  }
  
  def risingedge(x: Bool) = x && !Reg(next = x)

  val Data_Flit_Num = Reg(UInt(width = 3))
  Data_Flit_Num := io.Output_Header_Flit(1, 0)

  val First_cycle_of_transmit = Reg(UInt(), init = UInt(1))
  
  val Transmit_finished = Reg(UInt(), init = UInt(0))
  //val Transmit_finished = Bool(false)
  
  val Packet_ready = Reg(UInt(), init = UInt(0))
  Packet_ready := io.Packet_ready

  val Data_Flit_Counter = Reg(UInt(width = 3))
  io.Data_Flit_Counter_test := Data_Flit_Counter
  
  when(io.Input_Ready === UInt(1)) {
    when(io.Packet_ready === UInt(1) /*&& io.Input_Ready === UInt(1)*/) { 
      
      when(First_cycle_of_transmit === UInt(1)) {
        //Data_Flit_Counter := UInt(0)
        First_cycle_of_transmit := UInt(0)
        io.Output_Data_Valid := UInt(1)
      }
      
      when(risingedge(io.Input_ACK) || Transmit_finished === UInt(1)){
        io.Output_Data_Valid := UInt(0)
      }
      .otherwise {
        io.Output_Data_Valid := UInt(1)
      }
      
      when(Data_Flit_Counter <= Data_Flit_Num + UInt(1)) {
             
        when(Data_Flit_Counter === UInt(0) && risingedge(io.Output_Data_Valid)) {
          io.Output := io.Output_Header_Flit  
          Data_Flit_Counter := Data_Flit_Counter + UInt(1)       
        }
        .elsewhen(Data_Flit_Counter === UInt(1) && risingedge(io.Output_Data_Valid)) {
          io.Output := io.Output_Data_Flit_1   
          Data_Flit_Counter := Data_Flit_Counter + UInt(1)       
        }
        .elsewhen(Data_Flit_Counter === UInt(2) && risingedge(io.Output_Data_Valid)) {
          io.Output := io.Output_Data_Flit_2        
          Data_Flit_Counter := Data_Flit_Counter + UInt(1)       
        }
        .elsewhen(Data_Flit_Counter === UInt(3) && risingedge(io.Output_Data_Valid)) {
          io.Output := io.Output_Data_Flit_3       
          Data_Flit_Counter := Data_Flit_Counter + UInt(1)        
        }
        .elsewhen(Data_Flit_Counter === UInt(4) && risingedge(io.Output_Data_Valid)) {
          io.Output := io.Output_Data_Flit_4      
          Data_Flit_Counter := Data_Flit_Counter + UInt(1)       
        }       
      }
      .otherwise {
         io.Output_Data_Valid := UInt(0)
        when(risingedge(io.Input_ACK)){
         First_cycle_of_transmit := UInt(1)
          Transmit_finished := UInt(1)
        }
        .otherwise {
          Transmit_finished := UInt(0)
        }    
      }
    }
    .otherwise {
        io.Output := UInt(0)
        io.Output_Data_Valid := UInt(0)
        //Transmit_finished := UInt(0)
    }
  }
  .otherwise {
      io.Output := UInt(0)
      io.Output_Data_Valid := UInt(0)
      //Transmit_finished := UInt(0)
  }
  
  when(Data_Flit_Counter > Data_Flit_Num + UInt(1)) {
    when(risingedge(io.Input_ACK)) {
    io.Flits_Sent := UInt(1)
    Data_Flit_Counter := UInt(0)
    }
    .otherwise {
      io.Flits_Sent := UInt(0)
    }
  }
  .otherwise {
      io.Flits_Sent := UInt(0)
    }

}


class NetworkInterface_Input_port_Router extends Module {
  val io = new Bundle {
    /*IOs to and from Router*/
    val Input = UInt(INPUT, width = 8)
    val Input_Data_Valid = Bool(INPUT)   
    val Input_Ready = Bool(OUTPUT)
    val Input_ACK = UInt(OUTPUT,width = 1)
       
    /*IOs to and from Data decoder unit*/
    val Data_Valid_to_decoder = Bool(OUTPUT)
    val Output_Data_Flit_1 = UInt(OUTPUT, width = 8)
    val Output_Data_Flit_2 = UInt(OUTPUT, width = 8)
    val Output_Data_Flit_3 = UInt(OUTPUT, width = 8)
    val Output_Data_Flit_4 = UInt(OUTPUT, width = 8)
    
    val Decoder_ACK = Bool(INPUT)
   
  }
  
  def risingedge(x: Bool) = x && !Reg(next = x)
  
  val Input_ACK_Reg = Reg(UInt(), init = UInt(0))
  io.Input_ACK := Input_ACK_Reg
  
  
  val Input_Channel_Header_Flit = Reg(UInt(width = 8))
  val Input_Channel_Header_Flit_Full = Reg(UInt(),init = UInt(0))
  val Input_Channel_Data_1__Flit = Reg(UInt(width = 8))
  val Input_Channel_Data_1__Flit_Full = Reg(UInt(),init = UInt(0))
  val Input_Channel_Data_2__Flit = Reg(UInt(width = 8))
  val Input_Channel_Data_2__Flit_Full = Reg(UInt(),init = UInt(0))
  val Input_Channel_Data_3__Flit = Reg(UInt(width = 8))
  val Input_Channel_Data_3__Flit_Full = Reg(UInt(),init = UInt(0))
  val Input_Channel_Data_4__Flit = Reg(UInt(width = 8))
  val Input_Channel_Data_4__Flit_Full = Reg(UInt(),init = UInt(0))
 
  val Header_arrived = Reg(UInt(),init = UInt(0))
  
  val Flits_received = Reg(UInt(width=3),init = UInt(0))
  val Transmission_ongoing = Reg(UInt(width=1),init = UInt(0))
   
  
  // Indicate when all the input buffers are full
  when(Input_Channel_Header_Flit_Full === UInt(1) && Input_Channel_Data_1__Flit_Full === UInt(1) && Input_Channel_Data_2__Flit_Full === UInt(1) && Input_Channel_Data_3__Flit_Full === UInt(1) && Input_Channel_Data_4__Flit_Full === UInt(1)) {
    io.Input_Ready := UInt(0)
  }
  .otherwise {
     io.Input_Ready := UInt(1)
  }
 
  when(risingedge(io.Decoder_ACK)){
      Flits_received := UInt(0)
      Input_Channel_Header_Flit  := UInt(0)
      Input_Channel_Header_Flit_Full := UInt(0)
      Input_Channel_Data_1__Flit := UInt(0)
      Input_Channel_Data_1__Flit_Full := UInt(0)
      Input_Channel_Data_2__Flit := UInt(0)
      Input_Channel_Data_2__Flit_Full := UInt(0)
      Input_Channel_Data_3__Flit := UInt(0)
      Input_Channel_Data_3__Flit_Full := UInt(0)
      Input_Channel_Data_4__Flit := UInt(0)
      Input_Channel_Data_4__Flit_Full := UInt(0)
  }
  
  when(io.Input_Data_Valid && io.Input_ACK === UInt(0)) {
        when(Input_Channel_Header_Flit_Full === UInt(0) && Header_arrived === UInt(0)) {         
          Input_Channel_Header_Flit := io.Input
          Header_arrived := UInt(1)
          Input_Channel_Header_Flit_Full := UInt(1)
          Input_ACK_Reg := UInt(1)
          //Flits_received := UInt(0)
        }
        .elsewhen(Input_Channel_Data_1__Flit_Full === UInt(0)) {
          Input_Channel_Data_1__Flit := io.Input
          Input_Channel_Data_1__Flit_Full := UInt(1)
          Input_ACK_Reg := UInt(1) 
          Flits_received := Flits_received + UInt(1)
        }
        .elsewhen(Input_Channel_Data_2__Flit_Full === UInt(0)) {
          Input_Channel_Data_2__Flit := io.Input
          Input_Channel_Data_2__Flit_Full := UInt(1)
          Input_ACK_Reg := UInt(1)
          Flits_received := Flits_received + UInt(1)
        }
        .elsewhen(Input_Channel_Data_3__Flit_Full === UInt(0)) {
          Input_Channel_Data_3__Flit := io.Input
          Input_Channel_Data_3__Flit_Full := UInt(1)
          Input_ACK_Reg := UInt(1)
          Flits_received := Flits_received + UInt(1)
        }
        .elsewhen(Input_Channel_Data_4__Flit_Full === UInt(0)) {
          Input_Channel_Data_4__Flit := io.Input
          Input_Channel_Data_4__Flit_Full := UInt(1)
          Input_ACK_Reg := UInt(1)
          Flits_received := Flits_received + UInt(1)
        }
    }      
  .otherwise {
          Input_ACK_Reg := UInt(0)
  }
  
  when((Flits_received - UInt(1) === Input_Channel_Header_Flit(1,0)) && Header_arrived === UInt(1)) {
    io.Data_Valid_to_decoder := UInt(1)
    Header_arrived := UInt(0)
    io.Output_Data_Flit_1 := Input_Channel_Data_1__Flit
    io.Output_Data_Flit_2 := Input_Channel_Data_2__Flit
    io.Output_Data_Flit_3 := Input_Channel_Data_3__Flit
    io.Output_Data_Flit_4 := Input_Channel_Data_4__Flit
  }
  .otherwise {
     io.Data_Valid_to_decoder := UInt(0)
     io.Output_Data_Flit_1 := UInt(0)
     io.Output_Data_Flit_2 := UInt(0)
     io.Output_Data_Flit_3 := UInt(0)
     io.Output_Data_Flit_4 := UInt(0)
  }         
}


class NetworkInterface_Decoder_and_Output_to_Processor extends Module {
  val io = new Bundle {
      
    /*IOs to and from Input port Router*/
    val Data_Valid_to_decoder = Bool(INPUT)
    val Output_Data_Flit_1 = UInt(INPUT, width = 8)
    val Output_Data_Flit_2 = UInt(INPUT, width = 8)
    val Output_Data_Flit_3 = UInt(INPUT, width = 8)
    val Output_Data_Flit_4 = UInt(INPUT, width = 8)  
    val Decoder_ACK = Bool(OUTPUT)  
    
    // IOs to and from Processor
    val Output_Processor = UInt(OUTPUT,width=32)
    val Data_Valid_Processor = Bool(OUTPUT)
    
  }
  
  when(io.Data_Valid_to_decoder){
      io.Output_Processor := Cat(io.Output_Data_Flit_4,io.Output_Data_Flit_3,io.Output_Data_Flit_2,io.Output_Data_Flit_1)
      io.Data_Valid_Processor := UInt(1)
      io.Decoder_ACK := UInt(1)
  }
  .otherwise {
      io.Output_Processor := UInt(0)
      io.Data_Valid_Processor := UInt(0)
      io.Decoder_ACK := UInt(0)
  }
  
  
}


class NetworkInterface(Current_Proc_Number: Int) extends Module {
  val io = new Bundle {

    /*IOs for Processor side Input*/
    val Input_Processor = UInt(INPUT, width = 32)
    val Input_Valid_Processor = Bool(INPUT)
    val Data_ACK_Processor = Bool(OUTPUT)
    val Destination_Addr = UInt(INPUT, width = 4)
    val Input_Buffer_Full_from_Processor = Bool(OUTPUT)

    /*IOs for Router side Output*/
    val Output = UInt(OUTPUT, width = 8)
    val Output_Data_Valid = Bool(OUTPUT)
    val Input_ACK = Bool(INPUT)
    val Input_Ready = Bool(INPUT)
    
    //IOs from and to Router side Input
    val Router_Input = UInt(INPUT, width = 8)
    val Router_Input_Data_Valid = Bool(INPUT)   
    val Router_Input_Ready = Bool(OUTPUT)
    val Router_Input_ACK = UInt(OUTPUT,width = 1)
    
    //IOs to and from Processor side Output
    val Output_Processor = UInt(OUTPUT, width = 32)

    /*IOs for test************************************/
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
    val Output_Data_Flit_1_to_Decode = UInt(OUTPUT, width = 8)
    val Output_Data_Flit_2_to_Decode = UInt(OUTPUT, width = 8)
    val Output_Data_Flit_3_to_Decode = UInt(OUTPUT, width = 8)
    val Output_Data_Flit_4_to_Decode = UInt(OUTPUT, width = 8)
    
    /***************************************************/
  }

  val NetworkInterface_Input_Port_Processor = Module(new NetworkInterface_Input_Port_Processor)
  val NetworkInterface_Packet_Encapsulate = Module(new NetworkInterface_Packet_Encapsulate(Current_Proc_Number))
  val NetworkInterface_Port_Output = Module(new NetworkInterface_Port_Output)
  val NetworkInterface_Input_port_Router = Module (new NetworkInterface_Input_port_Router)
  val NetworkInterface_Decoder_and_Output_to_Processor = Module(new NetworkInterface_Decoder_and_Output_to_Processor)

  // Processor - Input Port
  NetworkInterface_Input_Port_Processor.io.Input_Processor := io.Input_Processor
  NetworkInterface_Input_Port_Processor.io.Input_Valid_Processor := io.Input_Valid_Processor
  io.Data_ACK_Processor := NetworkInterface_Input_Port_Processor.io.Data_ACK_Processor
  NetworkInterface_Input_Port_Processor.io.Destination_Addr := io.Destination_Addr
  io.Input_Buffer_Full_from_Processor := NetworkInterface_Input_Port_Processor.io.Input_Buffer_Full_from_Processor

  // Processor Input Port - Encapsulation Unit
  NetworkInterface_Packet_Encapsulate.io.InputBuffer_Data_0 := NetworkInterface_Input_Port_Processor.io.InputBuffer_Data_0
  NetworkInterface_Packet_Encapsulate.io.InputBuffer_Dest_0 := NetworkInterface_Input_Port_Processor.io.InputBuffer_Dest_0
  NetworkInterface_Packet_Encapsulate.io.InputBuffer_Full_0 := NetworkInterface_Input_Port_Processor.io.InputBuffer_Full_0
  NetworkInterface_Packet_Encapsulate.io.InputBuffer_Data_1 := NetworkInterface_Input_Port_Processor.io.InputBuffer_Data_1
  NetworkInterface_Packet_Encapsulate.io.InputBuffer_Dest_1 := NetworkInterface_Input_Port_Processor.io.InputBuffer_Dest_1
  NetworkInterface_Packet_Encapsulate.io.InputBuffer_Full_1 := NetworkInterface_Input_Port_Processor.io.InputBuffer_Full_1
  NetworkInterface_Input_Port_Processor.io.InputBuffer_Full_0_Empty := NetworkInterface_Packet_Encapsulate.io.InputBuffer_Full_0_Empty
  NetworkInterface_Input_Port_Processor.io.InputBuffer_Full_1_Empty := NetworkInterface_Packet_Encapsulate.io.InputBuffer_Full_1_Empty

  // Encapsulation Unit - Router Output Port
  io.Output := NetworkInterface_Port_Output.io.Output
  io.Output_Data_Valid := NetworkInterface_Port_Output.io.Output_Data_Valid
  NetworkInterface_Port_Output.io.Input_ACK := io.Input_ACK
  NetworkInterface_Port_Output.io.Input_Ready := io.Input_Ready
  NetworkInterface_Packet_Encapsulate.io.Flits_Sent := NetworkInterface_Port_Output.io.Flits_Sent
  NetworkInterface_Port_Output.io.Packet_ready := NetworkInterface_Packet_Encapsulate.io.Packet_ready
  NetworkInterface_Port_Output.io.Output_Header_Flit := NetworkInterface_Packet_Encapsulate.io.Output_Header_Flit
  NetworkInterface_Port_Output.io.Output_Data_Flit_1 := NetworkInterface_Packet_Encapsulate.io.Output_Data_Flit_1
  NetworkInterface_Port_Output.io.Output_Data_Flit_2 := NetworkInterface_Packet_Encapsulate.io.Output_Data_Flit_2
  NetworkInterface_Port_Output.io.Output_Data_Flit_3 := NetworkInterface_Packet_Encapsulate.io.Output_Data_Flit_3
  NetworkInterface_Port_Output.io.Output_Data_Flit_4 := NetworkInterface_Packet_Encapsulate.io.Output_Data_Flit_4

  //Router - Input Port
  NetworkInterface_Input_port_Router.io.Input := io.Router_Input
  NetworkInterface_Input_port_Router.io.Input_Data_Valid := io.Router_Input_Data_Valid
  io.Router_Input_Ready := NetworkInterface_Input_port_Router.io.Input_Ready 
  io.Router_Input_ACK := NetworkInterface_Input_port_Router.io.Input_ACK
  
  //Router Input Port - Decode Unit
  NetworkInterface_Decoder_and_Output_to_Processor.io.Output_Data_Flit_1 := NetworkInterface_Input_port_Router.io.Output_Data_Flit_1
  NetworkInterface_Decoder_and_Output_to_Processor.io.Output_Data_Flit_2 := NetworkInterface_Input_port_Router.io.Output_Data_Flit_2
  NetworkInterface_Decoder_and_Output_to_Processor.io.Output_Data_Flit_3 := NetworkInterface_Input_port_Router.io.Output_Data_Flit_3
  NetworkInterface_Decoder_and_Output_to_Processor.io.Output_Data_Flit_4 := NetworkInterface_Input_port_Router.io.Output_Data_Flit_4
  
  NetworkInterface_Decoder_and_Output_to_Processor.io.Data_Valid_to_decoder := NetworkInterface_Input_port_Router.io.Data_Valid_to_decoder
  NetworkInterface_Input_port_Router.io.Decoder_ACK := NetworkInterface_Decoder_and_Output_to_Processor.io.Decoder_ACK
  
  //Output Port - Processor 
  io.Output_Processor := NetworkInterface_Decoder_and_Output_to_Processor.io.Output_Processor
  
  
  
  /*FOR TESTING ****************************************************************************/
  io.InputBuffer_Data_0_test := NetworkInterface_Input_Port_Processor.io.InputBuffer_Data_0
  io.InputBuffer_Dest_0_test := NetworkInterface_Input_Port_Processor.io.InputBuffer_Dest_0
  io.InputBuffer_Full_0_test := NetworkInterface_Input_Port_Processor.io.InputBuffer_Full_0
  io.InputBuffer_Data_1_test := NetworkInterface_Input_Port_Processor.io.InputBuffer_Data_1
  io.InputBuffer_Dest_1_test := NetworkInterface_Input_Port_Processor.io.InputBuffer_Dest_1
  io.InputBuffer_Full_1_test := NetworkInterface_Input_Port_Processor.io.InputBuffer_Full_1
  io.Output_Header_Flit := NetworkInterface_Packet_Encapsulate.io.Output_Header_Flit
  io.Output_Data_Flit_1 := NetworkInterface_Packet_Encapsulate.io.Output_Data_Flit_1
  io.Output_Data_Flit_2 := NetworkInterface_Packet_Encapsulate.io.Output_Data_Flit_2
  io.Output_Data_Flit_3 := NetworkInterface_Packet_Encapsulate.io.Output_Data_Flit_3
  io.Output_Data_Flit_4 := NetworkInterface_Packet_Encapsulate.io.Output_Data_Flit_4
  io.Packet_ready := NetworkInterface_Packet_Encapsulate.io.Packet_ready
  io.Flits_Sent := NetworkInterface_Port_Output.io.Flits_Sent    
  io.Data_Flit_Counter_test := NetworkInterface_Port_Output.io.Data_Flit_Counter_test 
  io.Output_Buffers_ready_test := NetworkInterface_Packet_Encapsulate.io.Output_Buffers_ready_test 
  io.InputBuffer_Selector_test := NetworkInterface_Packet_Encapsulate.io.InputBuffer_Selector_test
  
  io.Data_Valid_to_Router := NetworkInterface_Port_Output.io.Output_Data_Valid

  
  /*****************************************************************************************/
}

class NetworkInterface_tests(c: NetworkInterface) extends Tester(c) {

  poke(c.io.Input_Valid_Processor, 1)
  poke(c.io.Input_Processor, 67305985)
  poke(c.io.Destination_Addr, 10)
  
  poke(c.io.Input_Ready,1)

  step(1)
  expect(c.io.InputBuffer_Data_0_test,67305985)
  expect(c.io.InputBuffer_Dest_0_test,10)
  expect(c.io.InputBuffer_Full_0_test,1)
  
  expect(c.io.InputBuffer_Data_1_test,0)
  expect(c.io.InputBuffer_Dest_1_test,0)
  expect(c.io.InputBuffer_Full_1_test,0)
  
  
  expect(c.io.InputBuffer_Selector_test,0)
  expect(c.io.Output_Header_Flit,0)
  expect(c.io.Output_Data_Flit_1,0)
  expect(c.io.Output_Data_Flit_2,0)
  expect(c.io.Output_Data_Flit_3,0)
  expect(c.io.Output_Data_Flit_4,0)
  
  expect(c.io.Data_ACK_Processor,1)
   expect(c.io.Output_Buffers_ready_test,1)
    
  step(1)
  
  poke(c.io.Input_Valid_Processor, 0)
  expect(c.io.Data_ACK_Processor,0)
  
  expect(c.io.InputBuffer_Data_0_test,67305985)
  expect(c.io.InputBuffer_Dest_0_test,10)
  expect(c.io.InputBuffer_Full_0_test,0)
  
  expect(c.io.InputBuffer_Data_1_test,0)
  expect(c.io.InputBuffer_Dest_1_test,0)
  expect(c.io.InputBuffer_Full_1_test,0)

  expect(c.io.InputBuffer_Selector_test,0)
  
  expect(c.io.Output_Header_Flit,0x27)
  expect(c.io.Output_Data_Flit_1,1)
  expect(c.io.Output_Data_Flit_2,2)
  expect(c.io.Output_Data_Flit_3,3)
  expect(c.io.Output_Data_Flit_4,4)
  
  expect(c.io.Packet_ready,1)
  expect(c.io.Output_Buffers_ready_test,0)
  
  step(1)
  
  poke(c.io.Input_Valid_Processor, 1)
  poke(c.io.Input_Processor, 16909060)
  poke(c.io.Destination_Addr, 10)
  
  expect(c.io.Packet_ready,1)
  expect(c.io.Output_Buffers_ready_test,0)
 
  expect(c.io.Output_Data_Valid,1)
  expect(c.io.Output,0x27)
  poke(c.io.Input_ACK,0)
  
  step(1)
  poke(c.io.Input_ACK,1)
  expect(c.io.Output_Data_Valid,0)
  expect(c.io.Output,0x00)
 
  step(1)
  
  expect(c.io.InputBuffer_Data_0_test,16909060)
  expect(c.io.InputBuffer_Dest_0_test,10)
  expect(c.io.InputBuffer_Full_0_test,1)
  expect(c.io.Output_Buffers_ready_test,0)
   
  poke(c.io.Input_ACK,0)
  expect(c.io.Output_Data_Valid,1)
  expect(c.io.Output,0x01)
  
  step(1)
   poke(c.io.Input_Valid_Processor, 0)
  
  poke(c.io.Input_ACK,1)
  expect(c.io.Output_Data_Valid,0)
  expect(c.io.Output,0x00)
 
  step(1)
  poke(c.io.Input_ACK,0)
  expect(c.io.Output_Data_Valid,1)
  expect(c.io.Output,0x02)
  
  step(1)
  poke(c.io.Input_ACK,1)
  expect(c.io.Output_Data_Valid,0)
  expect(c.io.Output,0x00)
  
  step(1)
  poke(c.io.Input_ACK,0)
  expect(c.io.Output_Data_Valid,1)
  expect(c.io.Output,0x03)
  
  step(1)
  poke(c.io.Input_ACK,1)
  expect(c.io.Output_Data_Valid,0)
  expect(c.io.Output,0x00)
  
  step(1)
  poke(c.io.Input_ACK,0)
  expect(c.io.Output_Data_Valid,1)
  expect(c.io.Output,0x04)
  
  step(1)
  poke(c.io.Input_ACK,1)
  expect(c.io.Output_Data_Valid,0)
  expect(c.io.Output,0x00)
  
  expect(c.io.Flits_Sent,1)
  
  step(1)
  poke(c.io.Input_ACK,0)
  expect(c.io.Output_Data_Valid,0)
  
  expect(c.io.Packet_ready,0)
  expect(c.io.Flits_Sent,0)
  
  expect(c.io.InputBuffer_Data_0_test,16909060)
  expect(c.io.InputBuffer_Dest_0_test,10)
  expect(c.io.InputBuffer_Full_0_test,1)
  expect(c.io.Output_Buffers_ready_test,1)
  
  step(1)
  expect(c.io.InputBuffer_Full_0_test,0)
  expect(c.io.Output_Header_Flit,0x27)
  expect(c.io.Output_Data_Flit_1,4)
  expect(c.io.Output_Data_Flit_2,3)
  expect(c.io.Output_Data_Flit_3,2)
  expect(c.io.Output_Data_Flit_4,1)
  
  expect(c.io.Packet_ready,1)
  expect(c.io.Output_Buffers_ready_test,0)
  
  step(1)
  expect(c.io.Packet_ready,1)
  expect(c.io.Output_Buffers_ready_test,0)
 
  expect(c.io.Output_Data_Valid,1)
  expect(c.io.Output,0x27)
  poke(c.io.Input_ACK,0)
  
  step(1)
  poke(c.io.Input_ACK,1)
  expect(c.io.Output_Data_Valid,0)
  expect(c.io.Output,0x00)
 
  step(1)
  
  poke(c.io.Input_ACK,0)
  expect(c.io.Output_Data_Valid,1)
  expect(c.io.Output,0x04)
  
  step(1)

  
  poke(c.io.Input_ACK,1)
  expect(c.io.Output_Data_Valid,0)
  expect(c.io.Output,0x00)
 
  step(1)
  poke(c.io.Input_ACK,0)
  expect(c.io.Output_Data_Valid,1)
  expect(c.io.Output,0x03)
  
  step(1)
  poke(c.io.Input_ACK,1)
  expect(c.io.Output_Data_Valid,0)
  expect(c.io.Output,0x00)
  
  step(1)
  poke(c.io.Input_ACK,0)
  expect(c.io.Output_Data_Valid,1)
  expect(c.io.Output,0x02)
  
  step(1)
  poke(c.io.Input_ACK,1)
  expect(c.io.Output_Data_Valid,0)
  expect(c.io.Output,0x00)
  
  step(1)
  poke(c.io.Input_ACK,0)
  expect(c.io.Output_Data_Valid,1)
  expect(c.io.Output,0x01)
  
  step(1)
  poke(c.io.Input_ACK,1)
  expect(c.io.Output_Data_Valid,0)
  expect(c.io.Output,0x00)
  
  expect(c.io.Flits_Sent,1)
  
  step(1)
  poke(c.io.Input_ACK,0)
  expect(c.io.Output_Data_Valid,0)
  
  expect(c.io.Packet_ready,0)
  expect(c.io.Flits_Sent,0)

}

object NetworkInterface {
  def main(args: Array[String]): Unit = {
    val tutArgs = args.slice(1, args.length)
    /* chiselMainTest(tutArgs, () => Module(new ArbiterUnit())) {
      c => new ArbiterUnit_tests(c) }*/
  }
}