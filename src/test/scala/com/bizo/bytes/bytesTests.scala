package com.bizo.bytes

import org.specs2._

class bytesTests extends Specification with matcher.DataTables {
  def is = s2"""

  The human-bytes format must follow the equivalence table when transformed to a string ${
              "bytes"  |     "SI"    |   "BINARY"   |
                    0L !        "0B" !         "0B" |
                   27L !       "27B" !        "27B" |
                  999L !      "999B" !       "999B" |
                 1000L !     "1.0kB" !      "1000B" |
                 1023L !     "1.0kB" !      "1023B" |
                 1024L !     "1.0kB" !     "1.0KiB" |
                 1728L !     "1.7kB" !     "1.7KiB" |
               110592L !   "110.6kB" !   "108.0KiB" |
              7077888L !     "7.1MB" !     "6.8MiB" |
            452984832L !   "453.0MB" !   "432.0MiB" |
          28991029248L !    "29.0GB" !    "27.0GiB" |
        1855425871872L !     "1.9TB" !     "1.7TiB" |
     9499780463984640L !     "9.5PB" !     "8.4PiB" |
  9223372036854775807L !     "9.2EB" !     "8.0EiB" |> { (a, b, c) => (SI(a), BIN(a)) must_== (b, c) }
  }

  The BINARY representation must follow the equivalence table ${
             "BINARY"  |        "bytes"       |
                   0.B !                   0L |
                  27.B !                  27L |
                 999.B !                 999L |
                1000.B !                1000L |
                1023.B !                1023L |
               1.0.KiB !                1024L |
               1.7.KiB !                1740L |
             108.0.KiB !              110592L |
               6.8.MiB !             7130316L |
             432.0.MiB !           452984832L |
              27.0.GiB !         28991029248L |
               1.7.TiB !       1869169767219L |
               8.4.PiB !    9457559217478041L |
               7.9.EiB ! 9108079886394091110L |> { _ must_== _}
  }

  The SI representation must follow the equivalence table ${
                "SI"   |        "bytes"       |
                   0.B !                   0L |
                  27.B !                  27L |
                 999.B !                 999L |
                1.0.kB !                1000L |
                1.7.kB !                1700L |
              110.6.kB !              110600L |
                7.1.MB !             7100000L |
              453.0.MB !           453000000L |
               29.0.GB !         29000000000L |
                1.9.TB !       1900000000000L |
                8.4.PB !    8400000000000000L |
                9.2.EB ! 9200000000000000000L |> { _ must_== _}
  }

  The SI representation should be parsed with the following rules ${
         "bytes"       | "SI-space"  | "SI-no space" |
                    0L !      "0B"   !          "0B" |
                   27L !     "27B"   !         "27B" |
                  999L !    "999B"   !        "999B" |
                 1000L !   "1.0kB"   !       "1.0kB" |
                 1700L !   "1.7kB"   !       "1.7kB" |
               110600L ! "110.6kB"   !     "110.6kB" |
              7100000L !   "7.1MB"   !       "7.1MB" |
            453000000L ! "453.0MB"   !     "453.0MB" |
          29000000000L !  "29.0GB"   !      "29.0GB" |
        1900000000000L !   "1.9TB"   !       "1.9TB" |
     8400000000000000L !   "8.4PB"   !       "8.4PB" |
  9200000000000000000L !   "9.2EB"   !       "9.2EB" |> { (a, b, c) => (humanBytesToLong(b), humanBytesToLong(c)) must_== (a, a)}
  }
  The Binary representation should be parsed with the following rules ${
           "bytes"     |  "BINARY-no space" | "BINARY-space"  |
                    0L !        "0B"        !        "0 B"    |
                   27L !       "27B"        !       "27 B"    |
                  999L !      "999B"        !      "999 B"    |
                 1000L !     "1000B"        !     "1000 B"    |
                 1023L !     "1023B"        !     "1023 B"    |
                 1024L !    "1.0KiB"        !    "1.0 KiB"    |
                 1740L !    "1.7KiB"        !    "1.7 KiB"    |
               110592L !  "108.0KiB"        !  "108.0 KiB"    |
              7130316L !    "6.8MiB"        !    "6.8 MiB"    |
            452984832L !  "432.0MiB"        !  "432.0 MiB"    |
          28991029248L !   "27.0GiB"        !   "27.0 GiB"    |
        1869169767219L !    "1.7TiB"        !    "1.7 TiB"    |
     9457559217478041L !    "8.4PiB"        !    "8.4 PiB"    |
  9108079886394091110L !    "7.9EiB"        !    "7.9 EiB"    |> { (a, b, c) => (humanBytesToLong(b), humanBytesToLong(c)) must_== (a, a)}
  }
  When the string parsed is not valid an IllegalArgumentException should be thrown ${
    humanBytesToLong("!B") must throwA[IllegalArgumentException]
  }
  """
}
