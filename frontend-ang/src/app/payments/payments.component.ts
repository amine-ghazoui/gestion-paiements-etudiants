import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';

@Component({
  selector: 'app-payments',
  standalone: false,
  templateUrl: './payments.component.html',
  styleUrls: ['./payments.component.css']
})
export class PaymentsComponent implements OnInit {

  public payments: any;
  public dataSource: any;
  public displayedColumns: string[] = ['id', 'date', 'amount', 'type', 'status', 'firstName'];

  // ! : signifie que la variable est initialisée plus tard
  /*
  c.-à-d. : je veux chercher dans la partie HTML un objet de type MatPaginator,
   après, je vais l'affecter à la variable paginator
   */
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  // Injection du service HttpClient pour effectuer des requêtes HTTP
  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    // Appel de l'API pour récupérer les paiements (envoyer une requête vers le backend)
    this.http.get("http://localhost:8021/payments")

      // La méthode subscribe permet d'écouter les réponses de l'API
      .subscribe({
        // next : méthode appelée lorsque la requête réussit
        next: data => {
          this.payments = data;
          this.dataSource = new MatTableDataSource(this.payments);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
        },
        // error : méthode appelée lorsque la requête échoue
        error: err => {
          console.log(err);
        }
      });
  }
}
