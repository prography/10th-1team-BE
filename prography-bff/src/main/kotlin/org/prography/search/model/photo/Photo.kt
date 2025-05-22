package org.prography.search.model.photo

import io.swagger.v3.oas.annotations.media.Schema

data class Photo(
    @Schema(
        example = "https://naverbooking-phinf.pstatic.net/20231226_188/1703556189805lzCCT_JPEG/%BD%BA%C5%B0%BE%DF%C5%B0_vip_%BB%F6%B0%A8%BA%B8%C1%A4_%B9%CC%B4%CF.jpg",
    ) val url: String,
)
