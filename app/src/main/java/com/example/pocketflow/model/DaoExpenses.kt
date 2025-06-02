package com.example.pocketflow.model

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pocketflow.model.Expenses

@Dao
interface DaoExpenses {
    @Insert
    fun insert(expense: Expenses)

    @Delete
    fun delete(expense: Expenses)

    @Query("SELECT * FROM Expenses WHERE idUser = :userId ORDER BY date DESC")
    fun getExpensesByUser(userId: Int): LiveData<List<Expenses>>

    @Query("SELECT * FROM Expenses WHERE idBudget = :budgetId ORDER BY date DESC")
    fun getExpensesByBudget(budgetId: Int): LiveData<List<Expenses>>
}