package com.bizo

/**
 * Package containing utilities to deal with the display of byte quantities.
 *
 * Features include
 * - a DSL to write human readable large quantities of bytes,
 * - formatting byte quantities from a Long,
 * - parsing byte quantities from a String.
 *
 * Following describes the supported standards:
 * +------------------------------------------------+
 * | Prefixes for multiples of bytes (B)            |
 * +------------------------------------------------+
 * | Decimal (SI)           Binary (IEC)            |
 * +----------------------+-------------------------+
 * | 1000      k (kilo)   | 1024       Ki (kibi)    |
 * | 1000^2    M (mega)   | 1024^2     Mi (mebi)    |
 * | 1000^3    G (giga)   | 1024^3     Gi (gibi)    |
 * | 1000^4    T (tera)   | 1024^4     Ti (tebi)    |
 * | 1000^5    P (peta)   | 1024^5     Pi (pebi)    |
 * | 1000^6    E (exa)    | 1024^6     Ei (exbi)    |
 * +----------------------+-------------------------+
 *
 * See http://en.wikipedia.org/wiki/Byte for a discussion on the subject.
 *
 * Note: The ambiguous JEDEC standard is not supported.
 *
 * ==Overview==
 *
 * Using the DSL:
 * @example {{{
 *    import com.bizo.bytes._
 *    // if you want to use the postfix notation (space between the value and the unit.)
 *    import scala.language.postfixOps
 *
 *    val sizeSI = 10.kB
 *    val sizeBinary = 10.KiB
 *    // or with the postfix notation
 *    val biggerSizeSI = 10 EB
 *    val biggerSizeBinary = 10 EiB
 * }}}
 *
 * Formatting a Long as byte quantities:
 * @example {{{
 *    import com.bizo.bytes._
 *
 *    println(SI(123456))  // prints 123.5kB
 *    println(BIN(123456)) // prints 120.6KiB
 * }}}
 *
 * Parsing byte quantities:
 * @example {{{
 *    import com.bizo.bytes._
 *
 *    val bytes = humanBytesToLong("123kB")  // 123000L
 *    val bytes = humanBytesToLong("123KiB") // 125952L
 * }}}
 *
 */
package object bytes {

  /**
   * Format as International System of Unit with one decimal when the unit is >= kB.
   * The rule is that the unit goes up when the value is dividable by 1000.
   * e.g. 1000B is displayed as 1kB.
   */
  def SI(l: Long): String = humanReadableBytes(l, si = true)

  /**
   * Format with the binary prefix with one decimal when the unit is >= KiB.
   * The rule is that the unit goes up when the value is dividable by 1024.
   * e.g. 1000B is displayed as 1000B, but 1024B is displayed 1KiB.
   */
  def BIN(l: Long): String = humanReadableBytes(l, si = false)

  //source: http://stackoverflow.com/a/3758880/157746 - thanks aioobe
  private def humanReadableBytes(bytes: Long, si: Boolean): String = {
    val unit = if (si) 1000 else 1024
    if (bytes < unit) return bytes + "B"
    val exp = (Math.log(bytes) / Math.log(unit)).toInt
    val pre = (if (si) "kMGTPE" else "KMGTPE").charAt(exp - 1) + (if (si) "" else "i")
    "%.1f%sB" format(bytes / Math.pow(unit, exp), pre)
  }

  /**
   * Parses a string representing a byte quantity using binary or SI notation. Decimal as well as integer are accepted.
   * Space and no space after the number are both valid. e.g. '1.1kB', '10B', '2.3 GiB'.
   * Multiple digits after the decimal place are allowed. e.g. '1.34kB', '10.123MB'. If the result doesn't end
   * with an exact number of bytes, the quantity will be rounded to the lower number.
   */
  def humanBytesToLong(s: String): Long = {
    import java.util.regex.Pattern
    
    val pattern = Pattern.compile("""(?<number>\d+(\.\d+)?)[ ]?(?<unit>(|k|Ki|M|Mi|G|Gi|T|Ti|P|Pi|E|Ei)B)""")

    val m = pattern.matcher(s)
    if (!m.matches())
      throw new IllegalArgumentException(
        s"The string $s doesn't correspond to a valid byte quantity format. " +
        s"Sample of valid quantity are '1.1kB', '10B', '2.3 GiB'. SI and binary notation are accepted.")

    (m.group("number").toDouble, m.group("unit")) match {
      case (d, "B")   => d.B
      case (d, "kB")  => d.kB
      case (d, "KiB") => d.KiB
      case (d, "MB")  => d.MB
      case (d, "MiB") => d.MiB
      case (d, "GB")  => d.GB
      case (d, "GiB") => d.GiB
      case (d, "TB")  => d.TB
      case (d, "TiB") => d.TiB
      case (d, "PB")  => d.PB
      case (d, "PiB") => d.PiB
      case (d, "EB")  => d.EB
      case (d, "EiB") => d.EiB
      case _ => throw new IllegalStateException
    }
  }

  implicit final class BytesInt(val n: Int) extends AnyVal with BytesConversions {
    override protected def toBytes(multiplier: Long): Long = n * multiplier
  }
  implicit final class BytesLong(val n: Long) extends AnyVal with BytesConversions {
    override protected def toBytes(multiplier: Long): Long = n * multiplier
  }
  implicit final class BytesDouble(val n: Double) extends AnyVal with BytesConversions {
    override protected def toBytes(multiplier: Long): Long = (BigDecimal(n) * multiplier).toLong
  }

  trait BytesConversions extends Any {

    protected def toBytes(multiplier: Long): Long

    def B   = toBytes(1)
    def kB  = toBytes(1000L)
    def KiB = toBytes(1024L)
    def MB  = toBytes(1000000L)//1000^2
    def MiB = toBytes(1048576L)//1024^2
    def GB  = toBytes(1000000000L)//1000^3
    def GiB = toBytes(1073741824L)//1024^3
    def TB  = toBytes(1000000000000L)//1000^4
    def TiB = toBytes(1099511627776L)//1024^4
    def PB  = toBytes(1000000000000000L)//1000^5
    def PiB = toBytes(1125899906842624L)//1024^5
    def EB  = toBytes(1000000000000000000L)//1000^6
    def EiB = toBytes(1152921504606846976L)//1024^6
  }

}