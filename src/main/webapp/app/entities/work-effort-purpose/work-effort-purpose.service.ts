import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWorkEffortPurpose } from 'app/shared/model/work-effort-purpose.model';

type EntityResponseType = HttpResponse<IWorkEffortPurpose>;
type EntityArrayResponseType = HttpResponse<IWorkEffortPurpose[]>;

@Injectable({ providedIn: 'root' })
export class WorkEffortPurposeService {
  public resourceUrl = SERVER_API_URL + 'api/work-effort-purposes';

  constructor(protected http: HttpClient) {}

  create(workEffortPurpose: IWorkEffortPurpose): Observable<EntityResponseType> {
    return this.http.post<IWorkEffortPurpose>(this.resourceUrl, workEffortPurpose, { observe: 'response' });
  }

  update(workEffortPurpose: IWorkEffortPurpose): Observable<EntityResponseType> {
    return this.http.put<IWorkEffortPurpose>(this.resourceUrl, workEffortPurpose, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWorkEffortPurpose>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWorkEffortPurpose[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
