package io.digitalheart.resume.models

data class Resume(
    val basics: Basics,
    val education: List<Education>,
    val languages: List<Language>,
    val work: List<Work>
)