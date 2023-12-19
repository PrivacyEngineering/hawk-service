package io.hawk.service.dlp

import io.hawk.dlp.common.Job
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DlpInputRestController {

    @PostMapping("/dlp/input")
    fun test(job: Job) {


    }
}