package cps.forest

import scala.quoted._
import scala.quoted.matching._

import cps._


class TypedTransform[F[_]:Type,T:Type](cpsCtx: TransformationContext[F,T])

  import cpsCtx._

  // case Apply(fun,args) 
  def run(given qctx: QuoteContext)(t: qctx.tasty.Term, tp: qctx.tasty.TypeTree): CpsExprResult[F,T] =
     import qctx.tasty.{_, given}
     val r = Async.rootTransform[F,T](t.seal.asInstanceOf[Expr[T]], asyncMonad, false)
     if (!r.haveAwait) 
       val build = CpsChunkBuilder.sync(asyncMonad, patternCode)
       CpsExprResult[F,T](patternCode, build, patternType, r.haveAwait)
     else
       // TODO:  create Typed with F[$tp] as type ?
       r
  


