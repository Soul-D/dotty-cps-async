package cps

import org.junit.{Test,Ignore}
import org.junit.Assert._

import scala.quoted._
import scala.util.Success


class TestBS1While


  @Test def tWhileC1_00(): Unit = 
     val c = async[ComputationBound]{
        val n = 10
        var s = 0
        var i = 0
        while(i < n) 
          s += i
          i += 1
        s
     }
     assert(c.run() == Success(45))


  @Test def tWhileC1_10(): Unit = 
     val c = async[ComputationBound]{
        val n = 10
        var s = 0
        var i = 0
        while(await( T1.cbBool(i < n) )) {
          s += i
          i += 1
        }
        s
     }
     assert(c.run() == Success(45))

  @Test def tWhileC1_01(): Unit = 
     val c = async[ComputationBound]{
        val n = 10
        var s = 0
        var i = 0
        while(i < n) {
          val q = await(T1.cbi(i))
          s += q
          i += 1
        }
        s
     }
     assert(c.run() == Success(45))

/*
 // Dotty crash.
 // TODO: minimize and submit bug.
 //  onlu this test : branch  dotty-break-while-00, if you want to minimize
 //  situation for bug submission - start there.
 //
  @Test def tWhileC1_11(): Unit = 
     val c = Async.transform[ComputationBound,Int]{
        val n = 10
        var s = 0
        var i = 0
        while(await(T1.cbBool(i < n))) {
          val q = await(T1.cbi(i))
          s += q
          i += 1
        }
        s
     }
     assert(c.run() == Success(45))

*/


