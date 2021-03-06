// CPS Transform for tasty apply
// (C) Ruslan Shevchenko <ruslan@shevchenko.kiev.ua>, 2019
package cps.forest

import scala.quoted._
import scala.quoted.matching._

import cps._


class ApplyTransform[F[_]:Type,T:Type](cpsCtx: TransformationContext[F,T])

  import cpsCtx._

  // case Apply(fun,args) 
  def run(given qctx: QuoteContext)(fun: qctx.tasty.Term, args: List[qctx.tasty.Term]): CpsExprResult[F,T] =
     import qctx.tasty.{_, given}
     println(s"!!! apply detected : ${fun} ${args}")
     ApplyTreeTransform.run(cpsCtx, patternCode.unseal, fun, args)
     

