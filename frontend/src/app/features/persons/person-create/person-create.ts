import {Component, inject} from '@angular/core';
import {FormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {PersonApi} from '../../../core/api/person-api';
import {Router} from '@angular/router';
import {CommonModule} from '@angular/common';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatButtonModule} from '@angular/material/button';

@Component({
  selector: 'app-person-create',
  standalone: true,
  imports: [  CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule],
  templateUrl: './person-create.html',
  styleUrl: './person-create.css',
})
export class PersonCreate {
  private fb = inject(FormBuilder);
  private api = inject(PersonApi);
  private router = inject(Router);

  form = this.fb.nonNullable.group({
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    zipCode: [''],
    city: [''],
    favoriteColor: ['']
  });

  colors = [
    'blau',
    'grün',
    'violett',
    'rot',
    'gelb',
    'türkis',
    'weiß'
  ];

  submit() {
    if (this.form.invalid) {
      return;
    }

    this.api.createPerson(this.form.getRawValue()).subscribe({
      next: () => this.router.navigate(['/persons']),
    });
  }

    cancel() {
      this.router.navigate(['/persons']);
    }
}
