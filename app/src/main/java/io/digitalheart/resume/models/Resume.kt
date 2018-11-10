package io.digitalheart.resume.models

data class Resume(
    val basics: Basics = Basics(),
    val education: List<Education> = emptyList(),
    val languages: List<Language> = emptyList(),
    val work: List<Work> = emptyList()
)