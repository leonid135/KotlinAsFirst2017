@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson7.task1

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {
    if (height <= 0 || width <= 0) throw IllegalArgumentException()
    return MatrixImpl(height, width, e)
}

fun <E> createMatrix(height: Int, width: Int, values: List<List<E>>): Matrix<E> {
    val matrix = createMatrix(height, width, values[0][0])
    for (row in 0 until height) {
        for (column in 0 until width) {
            matrix[row, column] = values[row][column]
        }
    }
    return matrix
}

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, val e: E) : Matrix<E> {

    private val content = MutableList(height) { MutableList(width) { e } }

    override fun get(row: Int, column: Int): E = content[row][column]


    override fun get(cell: Cell): E {
        if (cell.row !in 0 until height || cell.column !in 0 until width)
            throw IllegalArgumentException("Row ${cell.row}/$width, Col ${cell.column}/$height")
        return get(cell.row, cell.column)
    }


    override fun set(row: Int, column: Int, value: E) {
        content[row][column] = value
    }


    override fun set(cell: Cell, value: E) {
        set(cell.row, cell.column, value)
    }


    override fun equals(other: Any?): Boolean {
        if (!(other is Matrix<*> &&
                height == other.height &&
                width == other.width)) return false
        (0 until this.height).forEach { row ->
            (0 until this.width)
                    .filter { this[row, it] != other[row, it] }
                    .forEach { return false }
        }
        return true
    }


    override fun toString(): String {
        val strBuil = StringBuilder()
        strBuil.append("[")
        for (row in 0 until height) {
            strBuil.append("[")
            for (column in 0 until width) {
                strBuil.append(this[row, column])
                if (column != width - 1) strBuil.append(" ")
            }
            if (row != height - 1) strBuil.append("]\n") else strBuil.append("]")
        }
        strBuil.append("]")
        return "$strBuil"
    }
}


