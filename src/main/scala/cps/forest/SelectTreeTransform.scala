package cps.forest

import scala.quoted._
import scala.quoted.matching._

import cps._
import cps.misc._


trait SelectTreeTransform[F[_]]

  thisScope: TreeTransformScope[F] =>

  import qctx.tasty.{_, given}

  // case selectTerm @ Select(qualifier,name) 
  def runSelect( selectTerm: Select ): CpsTree =
     val symbol = selectTerm.symbol
     runRoot(selectTerm.qualifier).applyTerm(_.select(symbol), selectTerm.tpe)


object SelectTreeTransform


  def run[F[_]:Type,T:Type](given qctx: QuoteContext)(cpsCtx: TransformationContext[F,T],
                         selectTerm: qctx.tasty.Select): CpsExprResult[F,T] = {
                         
     val tmpFType = summon[Type[F]]
     class Bridge(tc:TransformationContext[F,T]) extends
                                                    TreeTransformScope[F]
                                                    with TreeTransformScopeInstance[F,T](tc) {

         implicit val fType: quoted.Type[F] = tmpFType
          
         def bridge(): CpsExprResult[F,T] =
            val origin = selectTerm.asInstanceOf[qctx.tasty.Select]
            runSelect(origin).toResult(cpsCtx.patternCode).asInstanceOf[CpsExprResult[F,T]]
                        

     } 
     (new Bridge(cpsCtx)).bridge()
  }

