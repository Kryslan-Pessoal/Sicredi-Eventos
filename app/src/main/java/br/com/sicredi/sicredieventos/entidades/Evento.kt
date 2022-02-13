package br.com.sicredi.sicredieventos.entidades

data class Evento(
    /** Id do item, usado para fazer check-in*/
    var id: Int,
    /** Título do Evento */
    var title: String,
    /** Descrição do Evento */
    var description: String,
    /** ??? */
    var people: Any,
    /** Data do Evento no formato ??? */
    var date: Long,
    /** Imagem de ícone para o evento */
    var image: String,
    /**  */
    var latitude: Long,
    /**  */
    var longitude: Long,
    /** Preço */
    var price: Double
)
