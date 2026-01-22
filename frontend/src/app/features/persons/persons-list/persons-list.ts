import {AfterViewInit, Component, inject, OnInit, signal, ViewChild} from '@angular/core';
import {PersonApi} from '../../../core/api/person-api';
import {CommonModule} from '@angular/common';
import {Person} from '../../../shared/models/person';
import {MatTableDataSource, MatTableModule} from '@angular/material/table';
import {MatSort, MatSortModule} from '@angular/material/sort';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import {Router, RouterModule} from '@angular/router';
import {MatButtonModule} from '@angular/material/button';

@Component({
  selector: 'app-persons-list',
  standalone: true,
  imports: [CommonModule, MatInputModule, MatTableModule, MatSortModule, MatFormFieldModule, MatProgressBarModule, MatButtonModule, RouterModule],
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


  constructor(private personApi: PersonApi, private router: Router) {}

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

    const colorOrderMap: Record<string, number> = {
      blau: 1,
      grün: 2,
      violett: 3,
      rot: 4,
      gelb: 5,
      türkis: 6,
      weiß: 7,
    };

    this.dataSource.sortingDataAccessor = (item, property) => {
      if (property === 'favoriteColor') {
        return colorOrderMap[item.favoriteColor ?? ''] ?? Number.MAX_SAFE_INTEGER;
      }
      return (item as any)[property] ?? '';
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

  openDetails(person: Person) {
    this.router.navigate(['/persons', person.id]);
  }

}
