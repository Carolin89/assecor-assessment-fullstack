import {Component, signal} from '@angular/core';
import {ActivatedRoute, RouterModule} from '@angular/router';
import {PersonApi} from '../../../core/api/person-api';
import { Person } from "../../../shared/models/person";
import {MatProgressBar} from '@angular/material/progress-bar';
import {MatError} from '@angular/material/input';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-person-detail',
  imports: [
    CommonModule,
    RouterModule,
    MatProgressBar,
    MatError
  ],
  templateUrl: './person-detail.html',
  styleUrl: './person-detail.css',
})
export class PersonDetail {

  readonly person = signal<Person | null>(null);
  readonly loading = signal(true);
  readonly error = signal<string | null>(null);

  constructor(
    private route: ActivatedRoute,
    private personApi: PersonApi
  ) {}

  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    if (Number.isNaN(id)) {
      this.error.set('Ungültige ID');
      this.loading.set(false);
      return;
    }

    this.personApi.getPersonById(id).subscribe({
      next: person => {
        this.person.set(person);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Person nicht gefunden');
        this.loading.set(false);
      }
    });
  }
}
