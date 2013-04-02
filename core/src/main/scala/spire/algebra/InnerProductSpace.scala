package spire.algebra

import scala.{ specialized => spec }
import scala.annotation.tailrec

trait InnerProductSpace[V, @spec(Int, Long, Float, Double) F] extends VectorSpace[V, F] { self =>
  def dot(v: V, w: V): F

  def normed(implicit ev: NRoot[F]): NormedVectorSpace[V, F] = new NormedInnerProductSpace[V, F] {
    def space = self
    def nroot = ev
  }
}

object InnerProductSpace {
  @inline final def apply[V, @spec(Int,Long,Float,Double) R](implicit V: InnerProductSpace[V, R]) = V
}

trait NormedInnerProductSpace[V, @spec(Float, Double) F] extends NormedVectorSpace[V, F] {
  def space: InnerProductSpace[V, F]
  def scalar: Field[F] = space.scalar
  def nroot: NRoot[F]

  def zero: V = space.zero
  def plus(v: V, w: V): V = space.plus(v, w)
  def negate(v: V): V = space.negate(v)
  override def minus(v: V, w: V): V = space.minus(v, w)
  def timesl(f: F, v: V): V = space.timesl(f, v)
  override def divr(v: V, f: F): V = space.divr(v, f)
  def norm(v: V): F = nroot.sqrt(space.dot(v, v))
}