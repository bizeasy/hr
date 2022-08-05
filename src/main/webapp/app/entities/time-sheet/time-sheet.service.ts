import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITimeSheet } from 'app/shared/model/time-sheet.model';

type EntityResponseType = HttpResponse<ITimeSheet>;
type EntityArrayResponseType = HttpResponse<ITimeSheet[]>;

@Injectable({ providedIn: 'root' })
export class TimeSheetService {
  public resourceUrl = SERVER_API_URL + 'api/time-sheets';

  constructor(protected http: HttpClient) {}

  create(timeSheet: ITimeSheet): Observable<EntityResponseType> {
    return this.http.post<ITimeSheet>(this.resourceUrl, timeSheet, { observe: 'response' });
  }

  update(timeSheet: ITimeSheet): Observable<EntityResponseType> {
    return this.http.put<ITimeSheet>(this.resourceUrl, timeSheet, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITimeSheet>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITimeSheet[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
