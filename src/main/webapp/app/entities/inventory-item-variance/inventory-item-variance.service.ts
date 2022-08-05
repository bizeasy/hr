import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IInventoryItemVariance } from 'app/shared/model/inventory-item-variance.model';

type EntityResponseType = HttpResponse<IInventoryItemVariance>;
type EntityArrayResponseType = HttpResponse<IInventoryItemVariance[]>;

@Injectable({ providedIn: 'root' })
export class InventoryItemVarianceService {
  public resourceUrl = SERVER_API_URL + 'api/inventory-item-variances';

  constructor(protected http: HttpClient) {}

  create(inventoryItemVariance: IInventoryItemVariance): Observable<EntityResponseType> {
    return this.http.post<IInventoryItemVariance>(this.resourceUrl, inventoryItemVariance, { observe: 'response' });
  }

  update(inventoryItemVariance: IInventoryItemVariance): Observable<EntityResponseType> {
    return this.http.put<IInventoryItemVariance>(this.resourceUrl, inventoryItemVariance, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInventoryItemVariance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInventoryItemVariance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
