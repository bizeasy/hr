import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmploymentAppSourceType } from 'app/shared/model/employment-app-source-type.model';

type EntityResponseType = HttpResponse<IEmploymentAppSourceType>;
type EntityArrayResponseType = HttpResponse<IEmploymentAppSourceType[]>;

@Injectable({ providedIn: 'root' })
export class EmploymentAppSourceTypeService {
  public resourceUrl = SERVER_API_URL + 'api/employment-app-source-types';

  constructor(protected http: HttpClient) {}

  create(employmentAppSourceType: IEmploymentAppSourceType): Observable<EntityResponseType> {
    return this.http.post<IEmploymentAppSourceType>(this.resourceUrl, employmentAppSourceType, { observe: 'response' });
  }

  update(employmentAppSourceType: IEmploymentAppSourceType): Observable<EntityResponseType> {
    return this.http.put<IEmploymentAppSourceType>(this.resourceUrl, employmentAppSourceType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmploymentAppSourceType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmploymentAppSourceType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
