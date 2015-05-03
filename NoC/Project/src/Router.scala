import Chisel._

class Input_port extends Module {
  val io = new Bundle {
    /*IOs to and from Network Interface*/
    val Input = UInt(INPUT, width = 8)
    val Data_Valid = Bool(INPUT)
    val Input_Ready = Bool(OUTPUT)
    val Input_ACK = UInt(OUTPUT, width = 1)

    /*IOs to and from ArbiterUnit*/
    val END = Bool(OUTPUT)
    val X_coord = UInt(OUTPUT, width = 3)
    val Y_coord = UInt(OUTPUT, width = 3)
    val Port_enable = Bool(INPUT)
    val Port_CH_select = Bool(INPUT)
    val Crossbar_ready = Bool(INPUT)

    /*IOs to and from Crossbar*/
    val Data_Out = UInt(OUTPUT, width = 8)
    val Output_ready = Bool(INPUT)
    val Data_Valid_to_Crossbar = Bool(OUTPUT)

  }

  val Input_ACK_Reg = Reg(UInt(), init = UInt(0))
  io.Input_ACK := Input_ACK_Reg

  val Input_Channel_Header_Flit = Reg(UInt(width = 8))
  val Input_Channel_Header_Flit_Full = Reg(UInt(), init = UInt(0))
  val Input_Channel_Data_Flit = Reg(UInt(width = 8))
  val Input_Channel_Data_Flit_Full = Reg(UInt(), init = UInt(0))

  val Header_arrived = Reg(UInt(), init = UInt(0))

  val Flits_transmitted = Reg(UInt(width = 3), init = UInt(0))
  val Transmission_ongoing = Reg(UInt(width = 1), init = UInt(0))

  // Indicate when all the input buffers are full
  when(Input_Channel_Header_Flit_Full === UInt(1) && Input_Channel_Data_Flit_Full === UInt(1) || (Flits_transmitted - UInt(1) === Input_Channel_Header_Flit(1, 0))) {
    io.Input_Ready := UInt(0)
  }
    .otherwise {
      io.Input_Ready := UInt(1)
    }

  when(io.Data_Valid && io.Input_ACK === UInt(0)) {
    when(Input_Channel_Header_Flit_Full === UInt(0) && Header_arrived === UInt(0)) {
      Input_Channel_Header_Flit := io.Input
      Header_arrived := UInt(1)
      Input_Channel_Header_Flit_Full := UInt(1)
      Input_ACK_Reg := UInt(1)
    }
      .elsewhen(Input_Channel_Data_Flit_Full === UInt(0)) {
        Input_Channel_Data_Flit := io.Input
        Input_Channel_Data_Flit_Full := UInt(1)
        Input_ACK_Reg := UInt(1)
      }
  }
    .otherwise {
      Input_ACK_Reg := UInt(0)
    }

  when(io.Port_enable === UInt(1) && Header_arrived === UInt(1)) {
    io.END := UInt(0)
    io.X_coord := Input_Channel_Header_Flit(7, 5)
    io.Y_coord := Input_Channel_Header_Flit(4, 2)
  }
    .otherwise {
      io.END := UInt(1)
      io.X_coord := UInt(0)
      io.Y_coord := UInt(0)
    }

  when(io.Crossbar_ready && io.Port_enable && io.Output_ready) {
    io.Data_Out := UInt(0xFA)
    when(Input_Channel_Header_Flit_Full === UInt(1) && Transmission_ongoing === UInt(0)) {
      io.Data_Valid_to_Crossbar := UInt(1)
      Transmission_ongoing := UInt(1)
      // Modify the coordinates before transmission according to XY         
      when(Input_Channel_Header_Flit(6, 5) != UInt(0)) { //If X coords are not zero, decrease them by 1
        io.Data_Out := Cat(Input_Channel_Header_Flit(7), Input_Channel_Header_Flit(6, 5) - UInt(1), Input_Channel_Header_Flit(4), Input_Channel_Header_Flit(3, 2), Input_Channel_Header_Flit(1, 0))
      }
        .elsewhen(Input_Channel_Header_Flit(3, 2) != UInt(0)) { //If Y coords are not zero, decrease them by 1
          io.Data_Out := Cat(Input_Channel_Header_Flit(7), Input_Channel_Header_Flit(6, 5), Input_Channel_Header_Flit(4), Input_Channel_Header_Flit(3, 2) - UInt(1), Input_Channel_Header_Flit(1, 0))
        }
        .otherwise { // This means X and Y coord are zeros, so leave them           
          io.Data_Out := Cat(Input_Channel_Header_Flit(7), Input_Channel_Header_Flit(6, 5), Input_Channel_Header_Flit(4), Input_Channel_Header_Flit(3, 2), Input_Channel_Header_Flit(1, 0))
        }

    }
      .elsewhen(Input_Channel_Data_Flit_Full === UInt(1)) {
        io.Data_Valid_to_Crossbar := UInt(1)
        io.Data_Out := Input_Channel_Data_Flit
        Input_Channel_Data_Flit_Full := UInt(0)
        Flits_transmitted := Flits_transmitted + UInt(1)
        Transmission_ongoing := UInt(1)
      }
      .otherwise {
        io.Data_Valid_to_Crossbar := UInt(0)
        io.Data_Out := UInt(0)
      }
  }
    .otherwise {
      io.Data_Out := UInt(0)
      io.Data_Valid_to_Crossbar := UInt(0)
    }

  when(Transmission_ongoing === UInt(1)) { // Needed because of the case when no transmission is ongoing but the channel is selected    
    when((Flits_transmitted - UInt(1) === Input_Channel_Header_Flit(1, 0)) && Header_arrived === UInt(1)) {
      Flits_transmitted := UInt(0)
      Header_arrived := UInt(0)
      Input_Channel_Header_Flit_Full := UInt(0)
      Transmission_ongoing := UInt(0)
    }
  }
}

class ArbiterUnit extends Module {
  val io = new Bundle {

    val Port1_END = Bool(INPUT)
    val Port1_X_coord = UInt(INPUT, width = 3)
    val Port1_Y_coord = UInt(INPUT, width = 3)

    val Port2_END = Bool(INPUT)
    val Port2_X_coord = UInt(INPUT, width = 3)
    val Port2_Y_coord = UInt(INPUT, width = 3)

    val Port3_END = Bool(INPUT)
    val Port3_X_coord = UInt(INPUT, width = 3)
    val Port3_Y_coord = UInt(INPUT, width = 3)

    val Port4_END = Bool(INPUT)
    val Port4_X_coord = UInt(INPUT, width = 3)
    val Port4_Y_coord = UInt(INPUT, width = 3)

    val PortP_END = Bool(INPUT)
    val PortP_X_coord = UInt(INPUT, width = 3)
    val PortP_Y_coord = UInt(INPUT, width = 3)

    val Port1_enable = Bool(OUTPUT)
    val Port2_enable = Bool(OUTPUT)
    val Port3_enable = Bool(OUTPUT)
    val Port4_enable = Bool(OUTPUT)
    val PortP_enable = Bool(OUTPUT)

    val Port1_input = UInt(INPUT, width = 8)
    val Port1_output = UInt(OUTPUT, width = 8)
    val Port1_to_input_Output_Ready = Bool(OUTPUT)
    val Port1_from_output_Output_Ready = Bool(INPUT)
    val Port1_from_input_Data_Valid = Bool(INPUT)
    val Port1_to_output_Data_Valid = Bool(OUTPUT)

    val Port2_input = UInt(INPUT, width = 8)
    val Port2_output = UInt(OUTPUT, width = 8)
    val Port2_to_input_Output_Ready = Bool(OUTPUT)
    val Port2_from_output_Output_Ready = Bool(INPUT)
    val Port2_from_input_Data_Valid = Bool(INPUT)
    val Port2_to_output_Data_Valid = Bool(OUTPUT)

    val Port3_input = UInt(INPUT, width = 8)
    val Port3_output = UInt(OUTPUT, width = 8)
    val Port3_to_input_Output_Ready = Bool(OUTPUT)
    val Port3_from_output_Output_Ready = Bool(INPUT)
    val Port3_from_input_Data_Valid = Bool(INPUT)
    val Port3_to_output_Data_Valid = Bool(OUTPUT)

    val Port4_input = UInt(INPUT, width = 8)
    val Port4_output = UInt(OUTPUT, width = 8)
    val Port4_to_input_Output_Ready = Bool(OUTPUT)
    val Port4_from_output_Output_Ready = Bool(INPUT)
    val Port4_from_input_Data_Valid = Bool(INPUT)
    val Port4_to_output_Data_Valid = Bool(OUTPUT)

    val PortP_input = UInt(INPUT, width = 8)
    val PortP_output = UInt(OUTPUT, width = 8)
    val PortP_to_input_Output_Ready = Bool(OUTPUT)
    val PortP_from_output_Output_Ready = Bool(INPUT)
    val PortP_from_input_Data_Valid = Bool(INPUT)
    val PortP_to_output_Data_Valid = Bool(OUTPUT)

    val Crossbar_ready = Bool(OUTPUT)

  }

  val End_reg = Reg(UInt(width = 1), init = UInt(1))
  val X_reg = Reg(UInt(width = 3), init = UInt(0))
  val Y_reg = Reg(UInt(width = 3), init = UInt(0))

  val P1 = Reg(UInt(), init = UInt(1))
  val P2 = Reg(UInt(), init = UInt(0))
  val P3 = Reg(UInt(), init = UInt(0))
  val P4 = Reg(UInt(), init = UInt(0))
  val PP = Reg(UInt(), init = UInt(0))

  val Shift_reg = Cat(PP, P4, P3, P2, P1)

  when(io.Port1_enable && io.Port1_END) {
    P1 := PP
    P2 := P1
    P3 := P2
    P4 := P3
    PP := P4
  }
    .elsewhen(io.Port2_enable && io.Port2_END) {
      P1 := PP
      P2 := P1
      P3 := P2
      P4 := P3
      PP := P4
    }
    .elsewhen(io.Port3_enable && io.Port3_END) {
      P1 := PP
      P2 := P1
      P3 := P2
      P4 := P3
      PP := P4
    }
    .elsewhen(io.Port4_enable && io.Port4_END) {
      P1 := PP
      P2 := P1
      P3 := P2
      P4 := P3
      PP := P4
    }
    .elsewhen(io.PortP_enable && io.PortP_END) {
      P1 := PP
      P2 := P1
      P3 := P2
      P4 := P3
      PP := P4
    }

  when(Shift_reg === UInt(1)) {
    io.Port1_enable := UInt(1)

    when(io.Port1_END) {
      End_reg := UInt(1)
    }
      .otherwise {
        End_reg := UInt(0)
      }
    X_reg := io.Port1_X_coord
    Y_reg := io.Port1_Y_coord

    io.Port2_enable := UInt(0)
    io.Port3_enable := UInt(0)
    io.Port4_enable := UInt(0)
    io.PortP_enable := UInt(0)

  }
    .elsewhen(Shift_reg === UInt(2)) {
      io.Port2_enable := UInt(1)

      when(io.Port2_END) {
        End_reg := UInt(1)
      }
        .otherwise {
          End_reg := UInt(0)
        }
      X_reg := io.Port2_X_coord
      Y_reg := io.Port2_Y_coord

      io.Port1_enable := UInt(0)
      io.Port3_enable := UInt(0)
      io.Port4_enable := UInt(0)
      io.PortP_enable := UInt(0)

    }
    .elsewhen(Shift_reg === UInt(4)) {
      io.Port3_enable := UInt(1)

      when(io.Port3_END) {
        End_reg := UInt(1)
      }
        .otherwise {
          End_reg := UInt(0)
        }
      X_reg := io.Port3_X_coord
      Y_reg := io.Port3_Y_coord

      io.Port1_enable := UInt(0)
      io.Port2_enable := UInt(0)
      io.Port4_enable := UInt(0)
      io.PortP_enable := UInt(0)

    }
    .elsewhen(Shift_reg === UInt(8)) {
      io.Port4_enable := UInt(1)

      when(io.Port4_END) {
        End_reg := UInt(1)
      }
        .otherwise {
          End_reg := UInt(0)
        }
      X_reg := io.Port4_X_coord
      Y_reg := io.Port4_Y_coord

      io.Port1_enable := UInt(0)
      io.Port2_enable := UInt(0)
      io.Port3_enable := UInt(0)
      io.PortP_enable := UInt(0)

    }
    .elsewhen(Shift_reg === UInt(16)) {
      io.PortP_enable := UInt(1)

      when(io.PortP_END) {
        End_reg := UInt(1)
      }
        .otherwise {
          End_reg := UInt(0)
        }
      X_reg := io.PortP_X_coord
      Y_reg := io.PortP_Y_coord

      io.Port1_enable := UInt(0)
      io.Port2_enable := UInt(0)
      io.Port3_enable := UInt(0)
      io.Port4_enable := UInt(0)

    }
    .otherwise {
      io.Port1_enable := UInt(0)
      io.Port2_enable := UInt(0)
      io.Port3_enable := UInt(0)
      io.Port4_enable := UInt(0)
      io.PortP_enable := UInt(0)
    }

  when(End_reg === UInt(0)) {
    when(X_reg(0) === UInt(0) && X_reg(1) === UInt(0)) { // X coords are 0
      when(Y_reg(0) === UInt(0) && Y_reg(1) === UInt(0)) { // Y coord are 0

        // Output is Port Processor
        when(io.Port1_enable === UInt(1)) {
          io.PortP_output := io.Port1_input
          io.Port1_to_input_Output_Ready := io.PortP_from_output_Output_Ready
          io.PortP_to_output_Data_Valid := io.Port1_from_input_Data_Valid
          io.Crossbar_ready := UInt(1)
        }
          .elsewhen(io.Port2_enable === UInt(1)) {
            io.PortP_output := io.Port2_input
            io.Port2_to_input_Output_Ready := io.PortP_from_output_Output_Ready
            io.PortP_to_output_Data_Valid := io.Port2_from_input_Data_Valid
            io.Crossbar_ready := UInt(1)
          }
          .elsewhen(io.Port3_enable === UInt(1)) {
            io.PortP_output := io.Port3_input
            io.Port3_to_input_Output_Ready := io.PortP_from_output_Output_Ready
            io.PortP_to_output_Data_Valid := io.Port3_from_input_Data_Valid
            io.Crossbar_ready := UInt(1)
          }
          .elsewhen(io.Port4_enable === UInt(1)) {
            io.PortP_output := io.Port4_input
            io.Port4_to_input_Output_Ready := io.PortP_from_output_Output_Ready
            io.PortP_to_output_Data_Valid := io.Port4_from_input_Data_Valid
            io.Crossbar_ready := UInt(1)
          }
          .elsewhen(io.PortP_enable === UInt(1)) {
            io.PortP_output := io.PortP_input
            io.PortP_to_input_Output_Ready := io.PortP_from_output_Output_Ready
            io.PortP_to_output_Data_Valid := io.PortP_from_input_Data_Valid
            io.Crossbar_ready := UInt(1)
          }
          .otherwise {
            io.Port1_output := UInt(0)
            io.Port2_output := UInt(0)
            io.Port3_output := UInt(0)
            io.Port4_output := UInt(0)
            io.PortP_output := UInt(0)
            io.Port1_to_input_Output_Ready := UInt(0)
            io.Port2_to_input_Output_Ready := UInt(0)
            io.Port3_to_input_Output_Ready := UInt(0)
            io.Port4_to_input_Output_Ready := UInt(0)
            io.PortP_to_input_Output_Ready := UInt(0)
            io.PortP_to_output_Data_Valid := UInt(0)
            io.Crossbar_ready := UInt(0)
          }
      }
        .elsewhen(Y_reg(2) === UInt(0)) { //Y coords not 0, Y positive

          // Output is Port4 (down)
          when(io.Port1_enable === UInt(1)) {
            io.Port4_output := io.Port1_input
            io.Port1_to_input_Output_Ready := io.Port4_from_output_Output_Ready
            io.Port4_to_output_Data_Valid := io.Port1_from_input_Data_Valid
            io.Crossbar_ready := UInt(1)
          }
            .elsewhen(io.Port2_enable === UInt(1)) {
              io.Port4_output := io.Port2_input
              io.Port2_to_input_Output_Ready := io.Port4_from_output_Output_Ready
              io.Port4_to_output_Data_Valid := io.Port2_from_input_Data_Valid
              io.Crossbar_ready := UInt(1)
            }
            .elsewhen(io.Port3_enable === UInt(1)) {
              io.Port4_output := io.Port3_input
              io.Port3_to_input_Output_Ready := io.Port4_from_output_Output_Ready
              io.Port4_to_output_Data_Valid := io.Port3_from_input_Data_Valid
              io.Crossbar_ready := UInt(1)
            }
            .elsewhen(io.Port4_enable === UInt(1)) {
              io.Port4_output := io.Port4_input
              io.Port4_to_input_Output_Ready := io.Port4_from_output_Output_Ready
              io.Port4_to_output_Data_Valid := io.Port4_from_input_Data_Valid
              io.Crossbar_ready := UInt(1)
            }
            .elsewhen(io.PortP_enable === UInt(1)) {
              io.Port4_output := io.PortP_input
              io.PortP_to_input_Output_Ready := io.Port4_from_output_Output_Ready
              io.Port4_to_output_Data_Valid := io.PortP_from_input_Data_Valid
              io.Crossbar_ready := UInt(1)
            }
            .otherwise {
              io.Port1_output := UInt(0)
              io.Port2_output := UInt(0)
              io.Port3_output := UInt(0)
              io.Port4_output := UInt(0)
              io.PortP_output := UInt(0)
              io.Port1_to_input_Output_Ready := UInt(0)
              io.Port2_to_input_Output_Ready := UInt(0)
              io.Port3_to_input_Output_Ready := UInt(0)
              io.Port4_to_input_Output_Ready := UInt(0)
              io.PortP_to_input_Output_Ready := UInt(0)
              io.Port4_to_output_Data_Valid := UInt(0)
              io.Crossbar_ready := UInt(0)
            }
        }
        .elsewhen(Y_reg(2) === UInt(1)) { //Y coords not 0, Y negative

          // Output is Port2 (up)
          when(io.Port1_enable === UInt(1)) {
            io.Port2_output := io.Port1_input
            io.Port1_to_input_Output_Ready := io.Port2_from_output_Output_Ready
            io.PortP_to_output_Data_Valid := io.Port1_from_input_Data_Valid
            io.Crossbar_ready := UInt(1)
          }
            .elsewhen(io.Port2_enable === UInt(1)) {
              io.Port2_output := io.Port2_input
              io.Port2_to_input_Output_Ready := io.Port2_from_output_Output_Ready
              io.Port2_to_output_Data_Valid := io.Port2_from_input_Data_Valid
              io.Crossbar_ready := UInt(1)
            }
            .elsewhen(io.Port3_enable === UInt(1)) {
              io.Port2_output := io.Port3_input
              io.Port3_to_input_Output_Ready := io.Port2_from_output_Output_Ready
              io.Port2_to_output_Data_Valid := io.Port3_from_input_Data_Valid
              io.Crossbar_ready := UInt(1)
            }
            .elsewhen(io.Port4_enable === UInt(1)) {
              io.Port2_output := io.Port4_input
              io.Port4_to_input_Output_Ready := io.Port2_from_output_Output_Ready
              io.Port2_to_output_Data_Valid := io.Port4_from_input_Data_Valid
              io.Crossbar_ready := UInt(1)
            }
            .elsewhen(io.PortP_enable === UInt(1)) {
              io.Port2_output := io.PortP_input
              io.PortP_to_input_Output_Ready := io.Port2_from_output_Output_Ready
              io.Port2_to_output_Data_Valid := io.PortP_from_input_Data_Valid
              io.Crossbar_ready := UInt(1)
            }
            .otherwise {
              io.Port1_output := UInt(0)
              io.Port2_output := UInt(0)
              io.Port3_output := UInt(0)
              io.Port4_output := UInt(0)
              io.PortP_output := UInt(0)
              io.Port1_to_input_Output_Ready := UInt(0)
              io.Port2_to_input_Output_Ready := UInt(0)
              io.Port3_to_input_Output_Ready := UInt(0)
              io.Port4_to_input_Output_Ready := UInt(0)
              io.PortP_to_input_Output_Ready := UInt(0)
              io.Port2_to_output_Data_Valid := UInt(0)
              io.Crossbar_ready := UInt(0)
            }
        }
    }
      .elsewhen((X_reg(2) === UInt(0))) { //X coords not 0, X positive

        // Output is Port3 (right)
        when(io.Port1_enable === UInt(1)) {
          io.Port3_output := io.Port1_input
          io.Port1_to_input_Output_Ready := io.Port3_from_output_Output_Ready
          io.Port3_to_output_Data_Valid := io.Port1_from_input_Data_Valid
          io.Crossbar_ready := UInt(1)
        }
          .elsewhen(io.Port2_enable === UInt(1)) {
            io.Port3_output := io.Port2_input
            io.Port2_to_input_Output_Ready := io.Port3_from_output_Output_Ready
            io.Port3_to_output_Data_Valid := io.Port2_from_input_Data_Valid
            io.Crossbar_ready := UInt(1)
          }
          .elsewhen(io.Port3_enable === UInt(1)) {
            io.Port3_output := io.Port3_input
            io.Port3_to_input_Output_Ready := io.Port3_from_output_Output_Ready
            io.Port3_to_output_Data_Valid := io.Port3_from_input_Data_Valid
            io.Crossbar_ready := UInt(1)
          }
          .elsewhen(io.Port4_enable === UInt(1)) {
            io.Port3_output := io.Port4_input
            io.Port4_to_input_Output_Ready := io.Port3_from_output_Output_Ready
            io.Port3_to_output_Data_Valid := io.Port4_from_input_Data_Valid
            io.Crossbar_ready := UInt(1)
          }
          .elsewhen(io.PortP_enable === UInt(1)) {
            io.Port3_output := io.PortP_input
            io.PortP_to_input_Output_Ready := io.Port3_from_output_Output_Ready
            io.Port3_to_output_Data_Valid := io.PortP_from_input_Data_Valid
            io.Crossbar_ready := UInt(1)
          }
          .otherwise {
            io.Port1_output := UInt(0)
            io.Port2_output := UInt(0)
            io.Port3_output := UInt(0)
            io.Port4_output := UInt(0)
            io.PortP_output := UInt(0)
            io.Port1_to_input_Output_Ready := UInt(0)
            io.Port2_to_input_Output_Ready := UInt(0)
            io.Port3_to_input_Output_Ready := UInt(0)
            io.Port4_to_input_Output_Ready := UInt(0)
            io.PortP_to_input_Output_Ready := UInt(0)
            io.Port3_to_output_Data_Valid := UInt(0)
            io.Crossbar_ready := UInt(0)
          }

      }
      .elsewhen((X_reg(2) === UInt(1))) { //X coords not 0, X negative

        // Output is Port1 (left)
        when(io.Port1_enable === UInt(1)) {
          io.Port1_output := io.Port1_input
          io.Port1_to_input_Output_Ready := io.Port1_from_output_Output_Ready
          io.Port1_to_output_Data_Valid := io.Port1_from_input_Data_Valid
          io.Crossbar_ready := UInt(1)
        }
          .elsewhen(io.Port2_enable === UInt(1)) {
            io.Port1_output := io.Port2_input
            io.Port2_to_input_Output_Ready := io.Port1_from_output_Output_Ready
            io.Port1_to_output_Data_Valid := io.Port2_from_input_Data_Valid
            io.Crossbar_ready := UInt(1)
          }
          .elsewhen(io.Port3_enable === UInt(1)) {
            io.Port1_output := io.Port3_input
            io.Port3_to_input_Output_Ready := io.Port1_from_output_Output_Ready
            io.Port1_to_output_Data_Valid := io.Port3_from_input_Data_Valid
            io.Crossbar_ready := UInt(1)
          }
          .elsewhen(io.Port4_enable === UInt(1)) {
            io.Port1_output := io.Port4_input
            io.Port4_to_input_Output_Ready := io.Port1_from_output_Output_Ready
            io.Port1_to_output_Data_Valid := io.Port4_from_input_Data_Valid
            io.Crossbar_ready := UInt(1)
          }
          .elsewhen(io.PortP_enable === UInt(1)) {
            io.Port1_output := io.PortP_input
            io.PortP_to_input_Output_Ready := io.Port1_from_output_Output_Ready
            io.Port1_to_output_Data_Valid := io.PortP_from_input_Data_Valid
            io.Crossbar_ready := UInt(1)
          }
          .otherwise {
            io.Port1_output := UInt(0)
            io.Port2_output := UInt(0)
            io.Port3_output := UInt(0)
            io.Port4_output := UInt(0)
            io.PortP_output := UInt(0)
            io.Port1_to_input_Output_Ready := UInt(0)
            io.Port2_to_input_Output_Ready := UInt(0)
            io.Port3_to_input_Output_Ready := UInt(0)
            io.Port4_to_input_Output_Ready := UInt(0)
            io.PortP_to_input_Output_Ready := UInt(0)
            io.Port1_to_output_Data_Valid := UInt(0)
            io.Crossbar_ready := UInt(0)
          }
      }
      .otherwise {
        io.Crossbar_ready := UInt(0)
        io.Port1_output := UInt(0)
        io.Port2_output := UInt(0)
        io.Port3_output := UInt(0)
        io.Port4_output := UInt(0)
        io.PortP_output := UInt(0)
        io.Port1_to_input_Output_Ready := UInt(0)
        io.Port2_to_input_Output_Ready := UInt(0)
        io.Port3_to_input_Output_Ready := UInt(0)
        io.Port4_to_input_Output_Ready := UInt(0)
        io.PortP_to_input_Output_Ready := UInt(0)
        io.Port1_to_output_Data_Valid := UInt(0)
        io.Port2_to_output_Data_Valid := UInt(0)
        io.Port3_to_output_Data_Valid := UInt(0)
        io.Port4_to_output_Data_Valid := UInt(0)
        io.PortP_to_output_Data_Valid := UInt(0)
      }
  }
    .otherwise {
      io.Crossbar_ready := UInt(0)
      io.Port1_output := UInt(0)
      io.Port2_output := UInt(0)
      io.Port3_output := UInt(0)
      io.Port4_output := UInt(0)
      io.PortP_output := UInt(0)
      io.Port1_to_input_Output_Ready := UInt(0)
      io.Port2_to_input_Output_Ready := UInt(0)
      io.Port3_to_input_Output_Ready := UInt(0)
      io.Port4_to_input_Output_Ready := UInt(0)
      io.PortP_to_input_Output_Ready := UInt(0)
      io.Port1_to_output_Data_Valid := UInt(0)
      io.Port2_to_output_Data_Valid := UInt(0)
      io.Port3_to_output_Data_Valid := UInt(0)
      io.Port4_to_output_Data_Valid := UInt(0)
      io.PortP_to_output_Data_Valid := UInt(0)
    }
}

class Output_port extends Module {
  val io = new Bundle {
    /*IOs to and from Crossbar*/
    val Input = UInt(INPUT, width = 8)
    val Output_Port_Ready = Bool(OUTPUT)
    val Data_Valid = Bool(INPUT)

    /*IOs to next Router/NI */
    val Output = UInt(OUTPUT, width = 8)
    val Data_Valid_out = Bool(OUTPUT)
    val Input_Ready = Bool(INPUT)
    val Input_ACK = Bool(INPUT)

  }

  def risingedge(x: Bool) = x && !Reg(next = x)
  val First_cycle_of_transmit = Reg(UInt(), init = UInt(1))
  val Transmit_finished = Reg(UInt(), init = UInt(1))

  when(io.Input_Ready && io.Input_ACK === UInt(0)) { // Checking the input ACK is necessary because the input port has to wait for the transmission's end before sending a new flit to the output port
    io.Output_Port_Ready := UInt(1)
  }
    .otherwise {
      io.Output_Port_Ready := UInt(0)
    }

  when(io.Data_Valid === UInt(1) && io.Input_Ready === UInt(1)) {

    when(First_cycle_of_transmit === UInt(1)) {
      First_cycle_of_transmit := UInt(0)
      io.Data_Valid_out := UInt(1)
    }

    when(risingedge(io.Input_ACK) /*|| Transmit_finished === UInt(1)*/ ) {
      Transmit_finished === UInt(1)
      io.Data_Valid_out := UInt(0)
    }
      .otherwise {
        Transmit_finished === UInt(0)
        io.Data_Valid_out := UInt(1)
      }

    io.Output := io.Input
  }
    .otherwise {
      io.Output := UInt(0)
      io.Data_Valid_out := UInt(0)
      Transmit_finished := UInt(0)
      First_cycle_of_transmit := UInt(1)
    }

}

/**
 * @author patmos
 */
class Router extends Module {
  val io = new Bundle {
    val Input_Processor_Input = UInt(INPUT, width = 8)
    val Input_Processor_Data_Valid = Bool(INPUT)
    val Input_Processor_Input_Ready = Bool(OUTPUT)
    val Input_Processor_Input_ACK = UInt(OUTPUT, width = 1)

    val Input_Port_1_Input = UInt(INPUT, width = 8)
    val Input_Port_1_Data_Valid = Bool(INPUT)
    val Input_Port_1_Input_Ready = Bool(OUTPUT)
    val Input_Port_1_Input_ACK = UInt(OUTPUT, width = 1)

    val Input_Port_2_Input = UInt(INPUT, width = 8)
    val Input_Port_2_Data_Valid = Bool(INPUT)
    val Input_Port_2_Input_Ready = Bool(OUTPUT)
    val Input_Port_2_Input_ACK = UInt(OUTPUT, width = 1)

    val Input_Port_3_Input = UInt(INPUT, width = 8)
    val Input_Port_3_Data_Valid = Bool(INPUT)
    val Input_Port_3_Input_Ready = Bool(OUTPUT)
    val Input_Port_3_Input_ACK = UInt(OUTPUT, width = 1)

    val Input_Port_4_Input = UInt(INPUT, width = 8)
    val Input_Port_4_Data_Valid = Bool(INPUT)
    val Input_Port_4_Input_Ready = Bool(OUTPUT)
    val Input_Port_4_Input_ACK = UInt(OUTPUT, width = 1)

    val Output_Port_1_Output = UInt(OUTPUT, width = 8)
    val Output_Port_1_Data_Valid = Bool(OUTPUT)
    val Output_Port_1_Ready = Bool(INPUT)
    val Output_Port_1_ACK = UInt(INPUT, width = 1)

    val Output_Port_2_Output = UInt(OUTPUT, width = 8)
    val Output_Port_2_Data_Valid = Bool(OUTPUT)
    val Output_Port_2_Ready = Bool(INPUT)
    val Output_Port_2_ACK = UInt(INPUT, width = 1)

    val Output_Port_3_Output = UInt(OUTPUT, width = 8)
    val Output_Port_3_Data_Valid = Bool(OUTPUT)
    val Output_Port_3_Ready = Bool(INPUT)
    val Output_Port_3_ACK = UInt(INPUT, width = 1)

    val Output_Port_4_Output = UInt(OUTPUT, width = 8)
    val Output_Port_4_Data_Valid = Bool(OUTPUT)
    val Output_Port_4_Ready = Bool(INPUT)
    val Output_Port_4_ACK = UInt(INPUT, width = 1)

    val Output_Port_P_Output = UInt(OUTPUT, width = 8)
    val Output_Port_P_Data_Valid = Bool(OUTPUT)
    val Output_Port_P_Ready = Bool(INPUT)
    val Output_Port_P_ACK = UInt(INPUT, width = 1)

  }

  val Processor_Input_port = Module(new Input_port)
  val Port_1_Input_port = Module(new Input_port)
  val Port_2_Input_port = Module(new Input_port)
  val Port_3_Input_port = Module(new Input_port)
  val Port_4_Input_port = Module(new Input_port)

  val Cross_Bar = Module(new ArbiterUnit)

  val Port_P_Output_port = Module(new Output_port)
  val Port_1_Output_port = Module(new Output_port)
  val Port_2_Output_port = Module(new Output_port)
  val Port_3_Output_port = Module(new Output_port)
  val Port_4_Output_port = Module(new Output_port)

  /*outside - Input ports*/
  //Port Processor
  Processor_Input_port.io.Input := io.Input_Processor_Input
  Processor_Input_port.io.Data_Valid := io.Input_Processor_Data_Valid
  io.Input_Processor_Input_Ready := Processor_Input_port.io.Input_Ready
  io.Input_Processor_Input_ACK := Processor_Input_port.io.Input_ACK

  //Port 1
  Port_1_Input_port.io.Input := io.Input_Port_1_Input
  Port_1_Input_port.io.Data_Valid := io.Input_Port_1_Data_Valid
  io.Input_Port_1_Input_Ready := Port_1_Input_port.io.Input_Ready
  io.Input_Port_1_Input_ACK := Port_1_Input_port.io.Input_ACK

  //Port 2
  Port_2_Input_port.io.Input := io.Input_Port_2_Input
  Port_2_Input_port.io.Data_Valid := io.Input_Port_2_Data_Valid
  io.Input_Port_2_Input_Ready := Port_2_Input_port.io.Input_Ready
  io.Input_Port_2_Input_ACK := Port_2_Input_port.io.Input_ACK

  //Port 3
  Port_3_Input_port.io.Input := io.Input_Port_3_Input
  Port_3_Input_port.io.Data_Valid := io.Input_Port_3_Data_Valid
  io.Input_Port_3_Input_Ready := Port_3_Input_port.io.Input_Ready
  io.Input_Port_3_Input_ACK := Port_3_Input_port.io.Input_ACK

  //Port 4
  Port_4_Input_port.io.Input := io.Input_Port_4_Input
  Port_4_Input_port.io.Data_Valid := io.Input_Port_4_Data_Valid
  io.Input_Port_4_Input_Ready := Port_4_Input_port.io.Input_Ready
  io.Input_Port_4_Input_ACK := Port_4_Input_port.io.Input_ACK

  /*Input ports - Crossbar*/
  //Port Processor
  Processor_Input_port.io.Port_enable := Cross_Bar.io.PortP_enable
  Cross_Bar.io.PortP_END := Processor_Input_port.io.END
  Cross_Bar.io.PortP_X_coord := Processor_Input_port.io.X_coord
  Cross_Bar.io.PortP_Y_coord := Processor_Input_port.io.Y_coord
  Cross_Bar.io.PortP_input := Processor_Input_port.io.Data_Out
  Cross_Bar.io.PortP_from_input_Data_Valid := Processor_Input_port.io.Data_Valid_to_Crossbar
  Processor_Input_port.io.Crossbar_ready := Cross_Bar.io.Crossbar_ready
  Processor_Input_port.io.Output_ready := Cross_Bar.io.PortP_to_input_Output_Ready
  //Port 1
  Port_1_Input_port.io.Port_enable := Cross_Bar.io.Port1_enable
  Cross_Bar.io.Port1_END := Port_1_Input_port.io.END
  Cross_Bar.io.Port1_X_coord := Port_1_Input_port.io.X_coord
  Cross_Bar.io.Port1_Y_coord := Port_1_Input_port.io.Y_coord
  Cross_Bar.io.Port1_input := Port_1_Input_port.io.Data_Out
  Cross_Bar.io.Port1_from_input_Data_Valid := Port_1_Input_port.io.Data_Valid_to_Crossbar
  Port_1_Input_port.io.Crossbar_ready := Cross_Bar.io.Crossbar_ready
  Port_1_Input_port.io.Output_ready := Cross_Bar.io.Port1_to_input_Output_Ready
  //Port 2
  Port_2_Input_port.io.Port_enable := Cross_Bar.io.Port2_enable
  Cross_Bar.io.Port2_END := Port_2_Input_port.io.END
  Cross_Bar.io.Port2_X_coord := Port_2_Input_port.io.X_coord
  Cross_Bar.io.Port2_Y_coord := Port_2_Input_port.io.Y_coord
  Cross_Bar.io.Port2_input := Port_2_Input_port.io.Data_Out
  Cross_Bar.io.Port2_from_input_Data_Valid := Port_2_Input_port.io.Data_Valid_to_Crossbar
  Port_2_Input_port.io.Crossbar_ready := Cross_Bar.io.Crossbar_ready
  Port_2_Input_port.io.Output_ready := Cross_Bar.io.Port2_to_input_Output_Ready
  //Port 3
  Port_3_Input_port.io.Port_enable := Cross_Bar.io.Port3_enable
  Cross_Bar.io.Port3_END := Port_3_Input_port.io.END
  Cross_Bar.io.Port3_X_coord := Port_3_Input_port.io.X_coord
  Cross_Bar.io.Port3_Y_coord := Port_3_Input_port.io.Y_coord
  Cross_Bar.io.Port3_input := Port_3_Input_port.io.Data_Out
  Cross_Bar.io.Port3_from_input_Data_Valid := Port_3_Input_port.io.Data_Valid_to_Crossbar
  Port_3_Input_port.io.Crossbar_ready := Cross_Bar.io.Crossbar_ready
  Port_3_Input_port.io.Output_ready := Cross_Bar.io.Port3_to_input_Output_Ready
  //Port 4
  Port_4_Input_port.io.Port_enable := Cross_Bar.io.Port4_enable
  Cross_Bar.io.Port4_END := Port_4_Input_port.io.END
  Cross_Bar.io.Port4_X_coord := Port_4_Input_port.io.X_coord
  Cross_Bar.io.Port4_Y_coord := Port_4_Input_port.io.Y_coord
  Cross_Bar.io.Port4_input := Port_4_Input_port.io.Data_Out
  Cross_Bar.io.Port4_from_input_Data_Valid := Port_4_Input_port.io.Data_Valid_to_Crossbar
  Port_4_Input_port.io.Crossbar_ready := Cross_Bar.io.Crossbar_ready
  Port_4_Input_port.io.Output_ready := Cross_Bar.io.Port4_to_input_Output_Ready

  /*Crossbar - Output ports*/
  //Port 1  
  Cross_Bar.io.Port1_from_output_Output_Ready := Port_1_Output_port.io.Output_Port_Ready
  Port_1_Output_port.io.Input := Cross_Bar.io.Port1_output
  Port_1_Output_port.io.Data_Valid := Cross_Bar.io.Port1_to_output_Data_Valid
  //Port 2  
  Cross_Bar.io.Port2_from_output_Output_Ready := Port_2_Output_port.io.Output_Port_Ready
  Port_2_Output_port.io.Input := Cross_Bar.io.Port2_output
  Port_2_Output_port.io.Data_Valid := Cross_Bar.io.Port2_to_output_Data_Valid
  //Port 3  
  Cross_Bar.io.Port3_from_output_Output_Ready := Port_3_Output_port.io.Output_Port_Ready
  Port_3_Output_port.io.Input := Cross_Bar.io.Port3_output
  Port_3_Output_port.io.Data_Valid := Cross_Bar.io.Port3_to_output_Data_Valid
  //Port 4  
  Cross_Bar.io.Port4_from_output_Output_Ready := Port_4_Output_port.io.Output_Port_Ready
  Port_4_Output_port.io.Input := Cross_Bar.io.Port4_output
  Port_4_Output_port.io.Data_Valid := Cross_Bar.io.Port4_to_output_Data_Valid
  //Port P  
  Cross_Bar.io.PortP_from_output_Output_Ready := Port_P_Output_port.io.Output_Port_Ready
  Port_P_Output_port.io.Input := Cross_Bar.io.PortP_output
  Port_P_Output_port.io.Data_Valid := Cross_Bar.io.PortP_to_output_Data_Valid

  /*Output ports - outside*/
  //Port 1
  io.Output_Port_1_Output := Port_1_Output_port.io.Output
  io.Output_Port_1_Data_Valid := Port_1_Output_port.io.Data_Valid_out
  Port_1_Output_port.io.Input_ACK := io.Output_Port_1_ACK
  Port_1_Output_port.io.Input_Ready := io.Output_Port_1_Ready
  //Port 2
  io.Output_Port_2_Output := Port_2_Output_port.io.Output
  io.Output_Port_2_Data_Valid := Port_2_Output_port.io.Data_Valid_out
  Port_2_Output_port.io.Input_ACK := io.Output_Port_2_ACK
  Port_2_Output_port.io.Input_Ready := io.Output_Port_2_Ready
  //Port 3
  io.Output_Port_3_Output := Port_3_Output_port.io.Output
  io.Output_Port_3_Data_Valid := Port_3_Output_port.io.Data_Valid_out
  Port_3_Output_port.io.Input_ACK := io.Output_Port_3_ACK
  Port_3_Output_port.io.Input_Ready := io.Output_Port_3_Ready
  //Port 4
  io.Output_Port_4_Output := Port_4_Output_port.io.Output
  io.Output_Port_4_Data_Valid := Port_4_Output_port.io.Data_Valid_out
  Port_4_Output_port.io.Input_ACK := io.Output_Port_4_ACK
  Port_4_Output_port.io.Input_Ready := io.Output_Port_4_Ready
  //Port P
  io.Output_Port_P_Output := Port_P_Output_port.io.Output
  io.Output_Port_P_Data_Valid := Port_P_Output_port.io.Data_Valid_out
  Port_P_Output_port.io.Input_ACK := io.Output_Port_P_ACK
  Port_P_Output_port.io.Input_Ready := io.Output_Port_P_Ready

}

object Router {
  def main(args: Array[String]): Unit = {
    val tutArgs = args.slice(1, args.length)
    /* chiselMainTest(tutArgs, () => Module(new ArbiterUnit())) {
      c => new ArbiterUnit_tests(c) }*/
  }
}