import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {MatTableDataSource} from '@angular/material/table';

@Component({
  selector: 'app-payments',
  standalone: false,
  templateUrl: './payments.component.html',
  styleUrl: './payments.component.css'
})
export class PaymentsComponent implements OnInit{

  public payments : any;
  public dataSource : any;
  public displayedColumns : string[] = ['id', 'date', 'amount', 'type', 'status','firstName'];
  // Injection du service HttpClient pour effectuer des requêtes HTTP
  constructor(private http : HttpClient) {
  }

  ngOnInit(): void {
    // Appel de l'API pour récupérer les paiements (envoyer une requête vers le backend)
    this.http.get("http://localhost:8021/payments")

      // la méthode subscribe permet d'écouter les réponses de l'API
      .subscribe({
        //next c'est la méthode qui est appelée lorsque la requête réussit
        next : data => {
          this.payments = data;
          this.dataSource = new MatTableDataSource(this.payments)
        },
        //error c'est la méthode qui est appelée lorsque la requête échoue
        error : err => {
          console.log(err);
        }
      })
  }

}

