import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDeductionType } from 'app/shared/model/deduction-type.model';

type EntityResponseType = HttpResponse<IDeductionType>;
type EntityArrayResponseType = HttpResponse<IDeductionType[]>;

@Injectable({ providedIn: 'root' })
export class DeductionTypeService {
  public resourceUrl = SERVER_API_URL + 'api/deduction-types';

  constructor(protected http: HttpClient) {}

  create(deductionType: IDeductionType): Observable<EntityResponseType> {
    return this.http.post<IDeductionType>(this.resourceUrl, deductionType, { observe: 'response' });
  }

  update(deductionType: IDeductionType): Observable<EntityResponseType> {
    return this.http.put<IDeductionType>(this.resourceUrl, deductionType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDeductionType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDeductionType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
