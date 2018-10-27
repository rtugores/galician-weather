package huitca1212.galicianweather.domain

interface DataPolicy

object Local : DataPolicy
object Network: DataPolicy
object LocalAndNetwork: DataPolicy