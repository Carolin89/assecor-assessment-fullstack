import {Component, inject} from '@angular/core';
import {PersonApi} from '../../../core/api/person-api';

@Component({
  selector: 'app-persons-list',
  imports: [],
  templateUrl: './persons-list.html',
  styleUrl: './persons-list.css',
})
export class PersonsList {
  private personApi = inject(PersonApi);
  ngOnInit() {
    this.personApi.getPersons().subscribe(data => {
      console.log('Persons from API:', data);
    });
  }

}
