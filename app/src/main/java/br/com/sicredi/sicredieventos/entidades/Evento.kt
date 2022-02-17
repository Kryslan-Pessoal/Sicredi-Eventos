package br.com.sicredi.sicrediEventos.entidades

data class Evento(
    /** Id do item, usado para fazer check-in*/
    var id: Int = 0,
    /** Título do Evento */
    var title: String = "",
    /** Descrição do Evento */
    var description: String = "",
    /** ??? */
    var people: Any? = null,
    /** Data do Evento no formato ??? */
    var date: Long = 0,
    /** Imagem de ícone para o evento */
    var image: String = "",
    /**  */
    var latitude: Double = 0.0,
    /**  */
    var longitude: Double = 0.0,
    /** Preço */
    var price: Double = 0.0
)