import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IWorkEffortInventoryProduced } from 'app/shared/model/work-effort-inventory-produced.model';

type EntityResponseType = HttpResponse<IWorkEffortInventoryProduced>;
type EntityArrayResponseType = HttpResponse<IWorkEffortInventoryProduced[]>;

@Injectable({ providedIn: 'root' })
export class WorkEffortInventoryProducedService {
  public resourceUrl = SERVER_API_URL + 'api/work-effort-inventory-produceds';

  constructor(protected http: HttpClient) {}

  create(workEffortInventoryProduced: IWorkEffortInventoryProduced): Observable<EntityResponseType> {
    return this.http.post<IWorkEffortInventoryProduced>(this.resourceUrl, workEffortInventoryProduced, { observe: 'response' });
  }

  update(workEffortInventoryProduced: IWorkEffortInventoryProduced): Observable<EntityResponseType> {
    return this.http.put<IWorkEffortInventoryProduced>(this.resourceUrl, workEffortInventoryProduced, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IWorkEffortInventoryProduced>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IWorkEffortInventoryProduced[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
