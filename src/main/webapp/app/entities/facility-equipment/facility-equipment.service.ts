import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFacilityEquipment } from 'app/shared/model/facility-equipment.model';

type EntityResponseType = HttpResponse<IFacilityEquipment>;
type EntityArrayResponseType = HttpResponse<IFacilityEquipment[]>;

@Injectable({ providedIn: 'root' })
export class FacilityEquipmentService {
  public resourceUrl = SERVER_API_URL + 'api/facility-equipments';

  constructor(protected http: HttpClient) {}

  create(facilityEquipment: IFacilityEquipment): Observable<EntityResponseType> {
    return this.http.post<IFacilityEquipment>(this.resourceUrl, facilityEquipment, { observe: 'response' });
  }

  update(facilityEquipment: IFacilityEquipment): Observable<EntityResponseType> {
    return this.http.put<IFacilityEquipment>(this.resourceUrl, facilityEquipment, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFacilityEquipment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFacilityEquipment[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
