package com.example.task7.di

import com.example.task7.presentation.form.fragment.FormFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [RepositoryBindsModule::class, ContextModule::class])
@Singleton
interface AppComponent {

    fun inject(formFragment: FormFragment)
}
