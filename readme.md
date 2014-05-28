human-bytes: human readable byte quantities
------------------------------------
### Intro ###

Package containing utilities to deal with the display of byte quantities.

Features include
  
 - a DSL to write human readable large quantities of bytes,
 - formatting byte quantities from a Long,
 - parsing byte quantities from a String.

<table>
<thead>
<tr>
  <th colspan="6">Prefixes used for bytes (B)</th>
</tr>
</thead>
<tbody>
<tr>
  <td colspan="3">Decimal (SI)</td>
  <td colspan="3">Binary (IEC)</td>
</tr>
<tr>
  <td>1000</td>
  <td>k</td>
  <td>(kilo)</td>
  <td>1024</td>
  <td>Ki</td>
  <td>(kibi)</td>
</tr>
<tr>
  <td>1000<sup>2</sup></td>
  <td>M</td>
  <td>(mega)</td>
  <td>1024<sup>2</sup></td>
  <td>Mi</td>
  <td>(mebi)</td>
</tr>
<tr>
  <td>1000<sup>3</sup></td>
  <td>G</td>
  <td>(giga)</td>
  <td>1024<sup>3</sup></td>
  <td>Gi</td>
  <td>(gibi)</td>
</tr>
<tr>
  <td>1000<sup>4</sup></td>
  <td>T</td>
  <td>(tera)</td>
  <td>1024<sup>4</sup></td>
  <td>Ti</td>
  <td>(tebi)</td>
</tr>
<tr>
  <td>1000<sup>5</sup></td>
  <td>P</td>
  <td>(peta)</td>
  <td>1024<sup>5</sup></td>
  <td>Pi</td>
  <td>(pebi)</td>
</tr>
<tr>
  <td>1000<sup>6</sup></td>
  <td>E</td>
  <td>(exa)</td>
  <td>1024<sup>6</sup></td>
  <td>Ei</td>
  <td>(exbi)</td>
</tr>
</tbody></table>

See http://en.wikipedia.org/wiki/Byte for a discussion on the subject.

_Note:_ The ambiguous JEDEC standard is not supported.

### Usage ###

#### The DSL ####
```scala
import com.bizo.bytes._
// if you want to use the postfix notation (space between the value and the unit.)
import scala.language.postfixOps

val sizeSI = 10.kB
val sizeBinary = 10.KiB
// or with the postfix notation
val biggerSizeSI = 10 EB
val biggerSizeBinary = 10 EiB
```
#### Formatting a Long as byte quantities ####
```scala
import com.bizo.bytes._

println(SI(123456))  // prints 123.5kB
println(BIN(123456)) // prints 120.6KiB
```
#### Parsing byte quantities ####
```scala
import com.bizo.bytes._

val bytes = humanBytesToLong("123kB")  // 123000L
val bytes = humanBytesToLong("123KiB") // 125952L
```
### Building ###

You need SBT 0.13 or higher.

    # Compile and package jars.
    sbt package

    # Generate API documentation.
    sbt doc

### Target platform ###

- Scala 2.10+

### License ###

human-bytes is is licensed under the terms of the
[Apache Software License v2.0](http://www.apache.org/licenses/LICENSE-2.0.html).
