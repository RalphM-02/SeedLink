package ph.edu.dlsu.mobdeve.g24.mco.seedlink

data class PostClass (
        var author: String,
        var image: ByteArray,
        var caption: String
        ) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as PostClass

                if (author != other.author) return false
                if (!image.contentEquals(other.image)) return false

                return true
        }

        override fun hashCode(): Int {
                var result = author.hashCode()
                result = 31 * result + image.contentHashCode()
                return result
        }
}