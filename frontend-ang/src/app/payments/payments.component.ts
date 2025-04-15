import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpClientModule} from '@angular/common/http';

@Component({
  selector: 'app-payments',
  standalone: false,
  templateUrl: './payments.component.html',
  styleUrl: './payments.component.css'
})
export class PaymentsComponent implements OnInit{

  public payments : any;
  constructor(private http : HttpClient) {
  }

  ngOnInit(): void {
    this.http.get("http://localhost:8080/payments")
      .subscribe({
        next : data => {
          this.payments = data;
        },
        error : err => {
          console.log(err);
        }
      })
  }

}
