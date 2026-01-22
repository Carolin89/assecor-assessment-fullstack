import {Component, inject, signal} from '@angular/core';
import {PersonApi} from '../../../core/api/person-api';
import {CommonModule} from '@angular/common';
import {Person} from '../../../shared/models/person';

@Component({
  selector: 'app-persons-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './persons-list.html',
  styleUrl: './persons-list.css',
})
export class PersonsList {
  private personApi = inject(PersonApi);

  readonly persons = signal<Person[]>([])
  readonly loading = signal(true);
  readonly error = signal<string | null>(null);

  ngOnInit() {
    this.loadPersons();
   /* this.personApi.getPersons().subscribe(data => {
      console.log('Persons from API:', data);
    });*/
  }

  private loadPersons(){
    this.personApi.getPersons().subscribe(
      {
        next: (persons) => {
          this.persons.set(persons);
          this.loading.set(false);
        },
        error: () => {
          this.error.set('Personen konnten nicht geladen werden');
          this.loading.set(false);
        }
      }
    )
  }

}
