package org.prography.search.service.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.prography.search.service.model.enumeration.SortingStrategy

class CursorTest {
    @Test
    fun `Cursor Encode & Decode 테스트`() {
        val encode = Cursor.encode("오엔_카페앤레스토랑@서울_강남구_압구정로11길_37-30")
        val cursor = Cursor.decode(encode, strategy = SortingStrategy.RELATED)

        Assertions.assertNull(cursor.key)
        Assertions.assertEquals("오엔_카페앤레스토랑@서울_강남구_압구정로11길_37-30", cursor.id)

        val encode2 = Cursor.encode(4.78, "오엔_카페앤레스토랑@서울_강남구_압구정로11길_37-30")
        val decode2 = Cursor.decode(encode2, strategy = SortingStrategy.AVERAGE_RATING_LOW)

        Assertions.assertEquals(4.78, decode2.key)
        Assertions.assertEquals("오엔_카페앤레스토랑@서울_강남구_압구정로11길_37-30", decode2.id)

        val encode3 = Cursor.encode(44213, "오엔_카페앤레스토랑@서울_강남구_압구정로11길_37-30")
        val decode3 = Cursor.decode(encode3, strategy = SortingStrategy.REVIEW_COUNT_LOW)

        Assertions.assertEquals(44213, decode3.key)
        Assertions.assertEquals("오엔_카페앤레스토랑@서울_강남구_압구정로11길_37-30", decode3.id)
    }
}
