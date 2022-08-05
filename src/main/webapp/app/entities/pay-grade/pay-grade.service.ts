import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPayGrade } from 'app/shared/model/pay-grade.model';

type EntityResponseType = HttpResponse<IPayGrade>;
type EntityArrayResponseType = HttpResponse<IPayGrade[]>;

@Injectable({ providedIn: 'root' })
export class PayGradeService {
  public resourceUrl = SERVER_API_URL + 'api/pay-grades';

  constructor(protected http: HttpClient) {}

  create(payGrade: IPayGrade): Observable<EntityResponseType> {
    return this.http.post<IPayGrade>(this.resourceUrl, payGrade, { observe: 'response' });
  }

  update(payGrade: IPayGrade): Observable<EntityResponseType> {
    return this.http.put<IPayGrade>(this.resourceUrl, payGrade, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPayGrade>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPayGrade[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
