// FULL_JDK
package test

class NodeCollection<NODE : Node<NODE>> private constructor(
    private val nodes: HashMap<Id<NODE>, NODE>) :
    Collection<NODE> by nodes.values,
    Map<Id<NODE>, NODE> by nodes
{
    constructor(nodeList: Collection<NODE>): this(
        nodes = nodeList.map { it.id to it }.toMap(LinkedHashMap()))

    override fun isEmpty() = nodes.isEmpty()

    override val size: Int get() = nodes.size

    override fun equals(other: Any?) = nodes == other

    override fun hashCode() = nodes.hashCode()

    override fun toString() = nodes.toString()
}

data class Id<out TYPE : Node<TYPE>>(val id: Long, val type: Class<out TYPE>)

interface Node<out T: Node<T>> {
    val id: Id<T>
}