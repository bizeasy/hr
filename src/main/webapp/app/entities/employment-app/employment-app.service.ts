import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmploymentApp } from 'app/shared/model/employment-app.model';

type EntityResponseType = HttpResponse<IEmploymentApp>;
type EntityArrayResponseType = HttpResponse<IEmploymentApp[]>;

@Injectable({ providedIn: 'root' })
export class EmploymentAppService {
  public resourceUrl = SERVER_API_URL + 'api/employment-apps';

  constructor(protected http: HttpClient) {}

  create(employmentApp: IEmploymentApp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employmentApp);
    return this.http
      .post<IEmploymentApp>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(employmentApp: IEmploymentApp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employmentApp);
    return this.http
      .put<IEmploymentApp>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmploymentApp>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmploymentApp[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(employmentApp: IEmploymentApp): IEmploymentApp {
    const copy: IEmploymentApp = Object.assign({}, employmentApp, {
      applicationDate:
        employmentApp.applicationDate && employmentApp.applicationDate.isValid() ? employmentApp.applicationDate.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.applicationDate = res.body.applicationDate ? moment(res.body.applicationDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((employmentApp: IEmploymentApp) => {
        employmentApp.applicationDate = employmentApp.applicationDate ? moment(employmentApp.applicationDate) : undefined;
      });
    }
    return res;
  }
}
