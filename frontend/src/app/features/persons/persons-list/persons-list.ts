import {AfterViewInit, Component, inject, OnInit, signal, ViewChild} from '@angular/core';
import {PersonApi} from '../../../core/api/person-api';
import {CommonModule} from '@angular/common';
import {Person} from '../../../shared/models/person';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MatSort, MatSortModule} from '@angular/material/sort';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressBarModule } from '@angular/material/progress-bar';

@Component({
  selector: 'app-persons-list',
  standalone: true,
  imports: [CommonModule, MatInputModule, MatTableModule, MatSortModule, MatFormFieldModule, MatProgressBarModule],
  templateUrl: './persons-list.html',
  styleUrl: './persons-list.css',
})
export class PersonsList implements OnInit, AfterViewInit{

  displayedColumns: string[] = [
    'lastName',
    'firstName',
    'zipCode',
    'city',
    'favoriteColor'
  ];

  dataSource = new MatTableDataSource<Person>([]);

  @ViewChild(MatSort) sort!: MatSort;
  readonly persons = signal<Person[]>([]);
  readonly loading = signal(true);
  readonly error = signal<string | null>(null);


  constructor(private personApi: PersonApi) {}

  ngOnInit() {
    this.personApi.getPersons().subscribe({
      next: persons => {
        this.persons.set(persons);
        this.dataSource.data = persons;
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Personen konnten nicht geladen werden');
        this.loading.set(false);
      }
    });
  }


  ngAfterViewInit() {
    this.dataSource.sort = this.sort;

    const colorOrder = [
      'blau',
      'grün',
      'violett',
      'rot',
      'gelb',
      'türkis',
      'weiß'
    ];

    this.dataSource.sortingDataAccessor = (item, property) => {
      if (property === 'favoriteColor') {
        return colorOrder.indexOf(item.favoriteColor ?? '');
      }
      return (item as any)[property];
    };


    this.dataSource.filterPredicate = (data: Person, filter: string) => {
      const search = filter.trim().toLowerCase();
      return (
        data.firstName.toLowerCase().includes(search) ||
        data.lastName.toLowerCase().includes(search) ||
        (data.city ?? '').toLowerCase().includes(search) ||
        (data.favoriteColor ?? '').toLowerCase().includes(search)
      );
    };
  }



  applyFilter(value: string) {
    this.dataSource.filter = value.trim().toLowerCase();
  }

}
